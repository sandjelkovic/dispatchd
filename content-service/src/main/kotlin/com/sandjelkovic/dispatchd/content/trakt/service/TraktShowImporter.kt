package com.sandjelkovic.dispatchd.content.trakt.service

import arrow.core.*
import com.sandjelkovic.dispatchd.content.api.ShowCreated
import com.sandjelkovic.dispatchd.content.api.ShowUpdateFailed
import com.sandjelkovic.dispatchd.content.api.ShowUpdated
import com.sandjelkovic.dispatchd.content.data.entity.Episode
import com.sandjelkovic.dispatchd.content.data.entity.Season
import com.sandjelkovic.dispatchd.content.data.entity.Show
import com.sandjelkovic.dispatchd.content.data.repository.EpisodeRepository
import com.sandjelkovic.dispatchd.content.data.repository.SeasonRepository
import com.sandjelkovic.dispatchd.content.data.repository.ShowRepository
import com.sandjelkovic.dispatchd.content.extensions.convert
import com.sandjelkovic.dispatchd.content.extensions.flatMapToOption
import com.sandjelkovic.dispatchd.content.service.*
import com.sandjelkovic.dispatchd.content.trakt.dto.EpisodeTrakt
import com.sandjelkovic.dispatchd.content.trakt.dto.SeasonTrakt
import com.sandjelkovic.dispatchd.content.trakt.dto.ShowTrakt
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktMediaProvider
import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.convert.ConversionService
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
open class TraktShowImporter(
    private val showRepository: ShowRepository,
    private val seasonRepository: SeasonRepository,
    private val episodeRepository: EpisodeRepository,
    private val conversionService: ConversionService,
    private val provider: TraktMediaProvider,
    private val eventPublisher: ApplicationEventPublisher
) : ShowImporter {
    companion object : KLogging() {
        const val traktHost = "trakt.tv"
    }

    override fun getIdentifier(uri: URI): Option<String> =
        Option.fromNullable(UriComponentsBuilder.fromUri(uri).build())
            .filter { it.pathSegments.size > 1 }
            .map { it.pathSegments[1] }


    override fun supports(host: String): Boolean = host == traktHost

    override fun importShow(showId: String): Either<ImportException, Show> =
        if (showRepository.findByTraktId(showId).isPresent) {
            Left(ShowAlreadyImportedException())
        } else getShowFromTrakt(showId)
            .flatMap(this@TraktShowImporter::enhanceShowWithData)
            .flatMap { show -> saveImportedShow(show) }
            .map { it.also { eventPublisher.publishEvent(ShowCreated(it.id.toString())) } }

    override fun refreshImportedShow(showId: String): Either<ImportException, Show> =
        showRepository.findByTraktId(showId).flatMapToOption().toEither { ShowDoesNotExistTraktException() }
            .flatMap { getShowFromTrakt(showId) }
            .flatMap(::enhanceShowWithData)
            .flatMap(::saveImportedShow)
            .map { it.also { eventPublisher.publishEvent(ShowUpdated(it.id.toString())) } }
            .mapLeft { it.also { eventPublisher.publishEvent(ShowUpdateFailed(showId)) } }

    // TODO move the logic to ShowService.
    fun saveImportedShow(show: Show): Either<ImportException, Show> {
        Optional.ofNullable(show.traktId)
            .flatMap(showRepository::findByTraktId)
            .ifPresent {
                show.id = it.id
                // TODO Merge
                seasonRepository.deleteAll(it.seasons) // delete old seasons
                // TODO Merge
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

    // TODO Refactor to make it easier to read.
    private fun enhanceShowWithData(show: Show): Either<ImportException, Show> =
        Either.right(show)
            .filterOrElse({ show.traktId != null }, { UnknownImportException() })
            .flatMap {
                val seasonsFuture = provider.getSeasonsAsync(show.traktId!!)
                val episodesFuture = provider.getShowEpisodesAsync(show.traktId!!)
                runBlocking {
                    getAndConvertSeasons(seasonsFuture)
                        .map { show.copy(seasons = it) }
                        .flatMap { innerShow ->
                            runBlocking {
                                getAndConvertEpisodes(episodesFuture)
                                    .map { innerShow.copy(episodes = it) }
                            }
                        }
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

    private fun getShowFromTrakt(showId: String) = getTraktShow(showId)
        .map { traktShow -> conversionService.convert<Show>(traktShow) }

    private suspend fun getAndConvertEpisodes(episodesFuture: AsyncResult<Try<List<EpisodeTrakt>>>): Either<ImportException, List<Episode>> =
        Either.catch { episodesFuture.get(10, TimeUnit.SECONDS)!! }
            .flatMap { it.toEither() }
            .mapLeft { RemoteServiceException(it.message ?: "") }
            .map { episodeTrakt -> conversionService.convert(episodeTrakt) }

    private suspend fun getAndConvertSeasons(seasonsFuture: AsyncResult<Try<List<SeasonTrakt>>>): Either<ImportException, List<Season>> =
        Either.catch { seasonsFuture.get(10, TimeUnit.SECONDS)!! }
            .flatMap { it.toEither() }
            .mapLeft { RemoteServiceException(it.message ?: "") }
            .map { seasons -> seasons.map { conversionService.convert(it) } }

    private fun getTraktShow(showId: String): Either<ImportException, ShowTrakt> {
        return provider.getShow(showId)
            .toEither { UnknownImportException() }
            .flatMap { it.toEither { ShowDoesNotExistTraktException() } }
    }
}
