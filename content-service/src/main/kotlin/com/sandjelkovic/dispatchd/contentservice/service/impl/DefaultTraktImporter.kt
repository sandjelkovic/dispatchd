package com.sandjelkovic.dispatchd.contentservice.service.impl

import com.sandjelkovic.dispatchd.contentservice.convert
import com.sandjelkovic.dispatchd.contentservice.data.entity.Episode
import com.sandjelkovic.dispatchd.contentservice.data.entity.Season
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import com.sandjelkovic.dispatchd.contentservice.data.repository.EpisodeRepository
import com.sandjelkovic.dispatchd.contentservice.data.repository.SeasonRepository
import com.sandjelkovic.dispatchd.contentservice.data.repository.ShowRepository
import com.sandjelkovic.dispatchd.contentservice.service.ShowDoesNotExistTraktException
import com.sandjelkovic.dispatchd.contentservice.service.TraktImporter
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktMediaProvider
import mu.KLogging
import org.springframework.core.convert.ConversionService
import org.springframework.stereotype.Service
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future

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
        val traktShow = provider.getShow(showId)
                .orElseThrow { ShowDoesNotExistTraktException() }

        val seasonsFuture = provider.getSeasonsAsync(showId)
        val episodesFuture = provider.getShowEpisodesAsync(showId)

        val show = showRepository.save(conversionService.convert<Show>(traktShow))

        val seasonMap = extractFromFutureOrDefault(seasonsFuture) { emptyList() }
                .map { seasonTrakt -> conversionService.convert<Season>(seasonTrakt) }
                .onEach { season -> season.show = show }
                .also { seasonRepository.saveAll(it) }
                .map { it.number to it }
                .toMap()

        extractFromFutureOrDefault(episodesFuture) { emptyList() }
                .map { episodeTrakt -> conversionService.convert<Episode>(episodeTrakt) }
                .onEach { episode ->
                    episode.show = show
                    episode.season = seasonMap.getOrElse(episode.seasonNumber, { Season() })
                }
                .also { episodeRepository.saveAll(it) }

        return showRepository.findById(show.id!!)
                .orElseThrow { RuntimeException("Error during import -> Show with ID ${show.id} is saved but can't be read") }
    }

    private fun <T> extractFromFutureOrDefault(future: Future<T>, default: () -> T): T =
            try {
                future.get()
            } catch (e: InterruptedException) {
                logger.error(e.message, e.cause)
                default()
            } catch (e: ExecutionException) {
                logger.error(e.message, e.cause)
                default()
            }
}
