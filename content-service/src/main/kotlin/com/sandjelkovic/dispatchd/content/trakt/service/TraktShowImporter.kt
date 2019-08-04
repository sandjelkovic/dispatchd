package com.sandjelkovic.dispatchd.content.trakt.service

import arrow.core.Either
import arrow.core.Option
import arrow.core.Success
import arrow.core.Try
import arrow.core.filterOrElse
import arrow.core.flatMap
import arrow.core.getOrDefault
import arrow.core.getOrElse
import arrow.syntax.function.pipe
import com.sandjelkovic.dispatchd.content.convert
import com.sandjelkovic.dispatchd.content.data.entity.Episode
import com.sandjelkovic.dispatchd.content.data.entity.Season
import com.sandjelkovic.dispatchd.content.data.entity.Show
import com.sandjelkovic.dispatchd.content.data.repository.EpisodeRepository
import com.sandjelkovic.dispatchd.content.data.repository.SeasonRepository
import com.sandjelkovic.dispatchd.content.data.repository.ShowRepository
import com.sandjelkovic.dispatchd.content.flatMapToOption
import com.sandjelkovic.dispatchd.content.service.ImportException
import com.sandjelkovic.dispatchd.content.service.ShowDoesNotExistTraktException
import com.sandjelkovic.dispatchd.content.service.ShowImporter
import com.sandjelkovic.dispatchd.content.service.UnknownImportException
import com.sandjelkovic.dispatchd.content.trakt.dto.EpisodeTrakt
import com.sandjelkovic.dispatchd.content.trakt.dto.SeasonTrakt
import com.sandjelkovic.dispatchd.content.trakt.dto.ShowTrakt
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktMediaProvider
import mu.KLogging
import org.springframework.core.convert.ConversionService
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.*
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

    override fun importShow(showId: String): Either<ImportException, Show> {
        return getShow(showId)
            .flatMap(this@TraktShowImporter::enhanceShowWithData)
            .flatMap(this@TraktShowImporter::saveImportedShow)
    }

    // TODO move the logic to ShowService.
    fun saveImportedShow(show: Show): Either<UnknownImportException, Show> {
        Optional.ofNullable(show.traktId)
            .flatMap(showRepository::findByTraktId)
            .ifPresent {
                show.id = it.id
                seasonRepository.deleteAll(it.seasons) // delete old seasons
                episodeRepository.deleteAll(it.episodes) // delete old episodes
                it.seasons = mutableListOf() // JPA/Hibernate Specifics
                it.episodes = mutableListOf() // JPA/Hibernate Specifics
                showRepository.save(it)
                // TODO Handle updates differently.
            }
        val preparedShow = connectEntitiesForPersistence(show)
        val savedShow = showRepository.save(preparedShow)
        seasonRepository.saveAll(preparedShow.seasons)
        episodeRepository.saveAll(preparedShow.episodes)
        return showRepository.findById(savedShow.id!!)
            .flatMapToOption()
            .toEither { UnknownImportException("Error during import -> Show with ID ${savedShow.id} is saved but can't be read") }
    }

    private fun enhanceShowWithData(show: Show): Either<ImportException, Show> =
        Either.right(show)
            .filterOrElse({ show.traktId != null }, { UnknownImportException() })
            .map {
                val seasonsFuture = provider.getSeasonsAsync(show.traktId!!)
                val episodesFuture = provider.getShowEpisodesAsync(show.traktId!!)

                val convertedSeasons = getAndConvertSeasons(seasonsFuture)

                show.apply {
                    seasons = convertedSeasons
                    episodes = getAndConvertEpisodes(episodesFuture)
                }
            }

    private fun connectEntitiesForPersistence(show: Show): Show = show.apply {
        val seasonMap: Map<String, Season> = show.seasons
            .map { (it.number ?: "") to it }
            .toMap()
            .onEach { it.value.show = show }

        show.episodes
            .onEach { episode ->
                episode.show = show
                episode.season = seasonMap.getOrElse(episode.seasonNumber ?: "", { Season() })
            }
    }

    private fun getShow(showId: String) = getTraktShow(showId)
        .map { traktShow -> conversionService.convert<Show>(traktShow) }

    private fun getAndConvertEpisodes(episodesFuture: AsyncResult<Try<List<EpisodeTrakt>>>): List<Episode> =
        extractFromFutureOrDefault(episodesFuture) { Success(emptyList()) }.getOrDefault { emptyList() }
            .map { episodeTrakt -> conversionService.convert<Episode>(episodeTrakt) }

    private fun getAndConvertSeasons(seasonsFuture: AsyncResult<Try<List<SeasonTrakt>>>): List<Season> {
        val seasons = extractFromFutureOrDefault(seasonsFuture) { Success(emptyList()) }.getOrElse { emptyList() }

        return seasons.map { seasonTrakt -> conversionService.convert<Season>(seasonTrakt) }
    }

    private fun getTraktShow(showId: String): Either<ImportException, ShowTrakt> {
        return provider.getShow(showId)
            .toEither { UnknownImportException() }
            .flatMap { it.toEither { ShowDoesNotExistTraktException() } }
    }

    private fun <T> extractFromFutureOrDefault(future: Future<T>, default: (Unit) -> T): T =
        Try { future.get() }
            .getOrElse { logger.warn(it.message, it) pipe default }
}
