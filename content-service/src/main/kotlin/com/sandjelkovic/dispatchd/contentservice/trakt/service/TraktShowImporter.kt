package com.sandjelkovic.dispatchd.contentservice.trakt.service

import arrow.core.Option
import com.sandjelkovic.dispatchd.contentservice.convert
import com.sandjelkovic.dispatchd.contentservice.data.entity.Episode
import com.sandjelkovic.dispatchd.contentservice.data.entity.Season
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import com.sandjelkovic.dispatchd.contentservice.data.repository.EpisodeRepository
import com.sandjelkovic.dispatchd.contentservice.data.repository.SeasonRepository
import com.sandjelkovic.dispatchd.contentservice.data.repository.ShowRepository
import com.sandjelkovic.dispatchd.contentservice.service.ShowDoesNotExistTraktException
import com.sandjelkovic.dispatchd.contentservice.service.ShowImporter
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktMediaProvider
import mu.KLogging
import org.springframework.core.convert.ConversionService
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
open class TraktShowImporter(val showRepository: ShowRepository,
                             val seasonRepository: SeasonRepository,
                             val episodeRepository: EpisodeRepository,
                             val conversionService: ConversionService,
                             val provider: TraktMediaProvider) : ShowImporter {
    companion object : KLogging()

    override fun getIdentifier(uri: URI): Option<String> = Option.just(UriComponentsBuilder.fromUri(uri).build())
            .map { it.pathSegments }
            .filter { it.size > 1 }
            .map { it[1] }


    override fun supports(host: String): Boolean = host == "trakt.com"

    // TODO Remove reliance on @Transactional
    @Transactional
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
