package com.sandjelkovic.dispatchd.contentservice.service.impl

import com.sandjelkovic.dispatchd.contentservice.convert
import com.sandjelkovic.dispatchd.contentservice.data.entity.Episode
import com.sandjelkovic.dispatchd.contentservice.data.entity.Season
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import com.sandjelkovic.dispatchd.contentservice.data.repository.EpisodeRepository
import com.sandjelkovic.dispatchd.contentservice.data.repository.SeasonRepository
import com.sandjelkovic.dispatchd.contentservice.data.repository.ShowRepository
import com.sandjelkovic.dispatchd.contentservice.service.RemoteServiceException
import com.sandjelkovic.dispatchd.contentservice.service.ShowDoesNotExistTraktException
import com.sandjelkovic.dispatchd.contentservice.service.TraktImporter
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.EpisodeTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.SeasonTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktMediaProvider
import mu.KLogging
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.web.client.HttpClientErrorException
import java.util.*
import java.util.concurrent.ExecutionException

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
@Service
class DefaultTraktImporter(val showRepository: ShowRepository,
                           val seasonRepository: SeasonRepository,
                           val episodeRepository: EpisodeRepository,
                           val conversionService: ConversionService,
                           val provider: TraktMediaProvider) : TraktImporter {

    companion object : KLogging()

    override fun importShow(showId: String): Show {
        val traktShow = getShowFromTrakt(showId)
                .orElseThrow { ShowDoesNotExistTraktException() }

        val seasonsFuture = provider.getSeasonsAsync(showId)
        val episodesFuture = provider.getShowEpisodesAsync(showId)

        val show = showRepository.save(conversionService.convert<Show>(traktShow))

        val seasonMap = retrieveAndConvertSeasons(seasonsFuture)
                .onEach { season -> season.show = show }
                .also { seasonRepository.saveAll(it) }
                .map { it.number to it }
                .toMap()

        retrieveAndConvertEpisodes(episodesFuture)
                .onEach { episode ->
                    episode.show = show
                    episode.season = seasonMap.getOrElse(episode.seasonNumber, { Season() })
                }
                .also { episodeRepository.saveAll(it) }

        return showRepository.findById(show.id!!)
                .orElseThrow { RuntimeException("Error during import -> Show with ID ${show.id} is saved but can't be read") }
    }


    private fun getShowFromTrakt(showId: String): Optional<ShowTrakt> =
            try {
                provider.getShow(showId)
            } catch (e: HttpClientErrorException) {
                throw RemoteServiceException(e)
            }

    private fun retrieveAndConvertSeasons(seasonsFuture: ListenableFuture<List<SeasonTrakt>>): List<Season> {
        return retrieveSeasonsFromFuture(seasonsFuture)
                .map { seasonTrakt -> conversionService.convert<Season>(seasonTrakt) }
    }

    private fun retrieveSeasonsFromFuture(seasonsFuture: ListenableFuture<List<SeasonTrakt>>): List<SeasonTrakt> =
            try {
                seasonsFuture.get()
            } catch (e: InterruptedException) {
                logger.error(e.message, e.cause)
                listOf()
            } catch (e: ExecutionException) {
                logger.error(e.message, e.cause)
                listOf()
            }

    private fun retrieveAndConvertEpisodes(episodesFuture: ListenableFuture<List<EpisodeTrakt>>): List<Episode> {
        return retrieveEpisodesFromFuture(episodesFuture)
                .map { episodeTrakt -> conversionService.convert<Episode>(episodeTrakt) }
    }

    private fun retrieveEpisodesFromFuture(episodesFuture: ListenableFuture<List<EpisodeTrakt>>): List<EpisodeTrakt> =
            try {
                episodesFuture.get()!!
            } catch (e: InterruptedException) {
                logger.error(e.message, e.cause)
                listOf()
            } catch (e: ExecutionException) {
                logger.error(e.message, e.cause)
                listOf()
            }
}
