package com.sandjelkovic.dispatchd.content.trakt.service

import arrow.core.*
import arrow.syntax.function.pipe
import com.sandjelkovic.dispatchd.content.convert
import com.sandjelkovic.dispatchd.content.data.entity.Episode
import com.sandjelkovic.dispatchd.content.data.entity.Season
import com.sandjelkovic.dispatchd.content.data.entity.Show
import com.sandjelkovic.dispatchd.content.data.repository.EpisodeRepository
import com.sandjelkovic.dispatchd.content.data.repository.SeasonRepository
import com.sandjelkovic.dispatchd.content.data.repository.ShowRepository
import com.sandjelkovic.dispatchd.content.flatMapToOption
import com.sandjelkovic.dispatchd.content.service.*
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktMediaProvider
import mu.KLogging
import org.springframework.core.convert.ConversionService
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.concurrent.Future

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
open class TraktShowImporter(
    private val showRepository: ShowRepository,
    private val seasonRepository: SeasonRepository,
    private val episodeRepository: EpisodeRepository,
    private val conversionService: ConversionService,
    private val provider: TraktMediaProvider
) : ShowImporter {
    companion object : KLogging() {
        const val traktHost = "trakt.tv"
    }

    override fun getIdentifier(uri: URI): Option<String> =
        Option.fromNullable(UriComponentsBuilder.fromUri(uri).build())
            .filter { it.pathSegments.size > 1 }
            .map { it.pathSegments[1] }


    override fun supports(host: String): Boolean = host == traktHost

    // TODO Remove reliance on @Transactional
    @Transactional
    override fun importShow(showId: String): Either<ImportException, Show> {
        return provider.getShow(showId)
            .toEither { UnknownImportException() }
            .flatMap { it.toEither { ShowDoesNotExistTraktException() } }
            .flatMap { show ->
                showRepository.findByTraktId(show.ids["trakt"] ?: showId)
                    .flatMapToOption()
                    .toEither { ShowAlreadyImportedException() }
            }
            .flatMap { traktShow ->
                val seasonsFuture = provider.getSeasonsAsync(showId)
                val episodesFuture = provider.getShowEpisodesAsync(showId)

                val savedShow = showRepository.save(conversionService.convert(traktShow))

                val seasonMap =
                    extractFromFutureOrDefault(seasonsFuture) { Success(emptyList()) }.getOrElse { emptyList() }
                        .map { seasonTrakt -> conversionService.convert<Season>(seasonTrakt) }
                        .onEach { season -> season.show = savedShow }
                        .also { seasonRepository.saveAll(it) }
                        .map { it.number to it }
                        .toMap()

                extractFromFutureOrDefault(episodesFuture) { Success(emptyList()) }.getOrDefault { emptyList() }
                    .map { episodeTrakt -> conversionService.convert<Episode>(episodeTrakt) }
                    .onEach { episode ->
                        episode.show = savedShow
                        episode.season = seasonMap.getOrElse(episode.seasonNumber, { Season() })
                    }
                    .also { episodeRepository.saveAll(it) }
                showRepository.findById(savedShow.id!!)
                    .flatMapToOption()
                    .toEither { UnknownImportException("Error during import -> Show with ID ${savedShow.id} is saved but can't be read") }
            }
    }

    private fun <T> extractFromFutureOrDefault(future: Future<T>, default: (Unit) -> T): T =
        Try { future.get() }
            .getOrElse { logger.warn(it.message, it) pipe default }
}
