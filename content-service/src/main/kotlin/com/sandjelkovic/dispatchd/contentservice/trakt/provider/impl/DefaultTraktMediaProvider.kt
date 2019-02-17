package com.sandjelkovic.dispatchd.contentservice.trakt.provider.impl

import arrow.core.*
import arrow.syntax.function.pipe
import com.sandjelkovic.dispatchd.contentservice.service.RemoteServiceException
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.EpisodeTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.SeasonTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowUpdateTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktMediaProvider
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktUriProvider
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.time.LocalDate

/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
open class DefaultTraktMediaProvider(
    private val traktUriProvider: TraktUriProvider,
    private val traktRestTemplate: RestTemplate
) : TraktMediaProvider {
    companion object : KLogging()

    override fun getShow(showId: String): Try<Option<ShowTrakt>> {
        val uri = traktUriProvider.getShowUri(showId)

        return Try { traktRestTemplate.getForObject<ShowTrakt>(uri) }
            .map { it.toOption() }
            .recoverWith(
                partialRecovery(::isHttpClientErrorAndNotFound) { Success(Option.empty<ShowTrakt>()) }
            )
    }

    fun <T> partialRecovery(
        predicate: (Throwable) -> Boolean,
        success: (Throwable) -> Success<T>
    ): (Throwable) -> Try<T> =
        { throwable: Throwable ->
            when {
                predicate(throwable) -> success(throwable)
                else -> Failure(throwable)
            }
        }

    private fun isHttpClientErrorAndNotFound(it: Throwable) = it is HttpClientErrorException && isNotFound(it)

    private fun isNotFound(it: HttpClientErrorException) = HttpStatus.NOT_FOUND == it.statusCode

    override fun getSeasons(showId: String): List<SeasonTrakt> {
        val uri = traktUriProvider.getSeasonsUri(showId)
        val seasons: Array<SeasonTrakt>? = executeHttpOrMapException { traktRestTemplate.getForObject(uri) }

        logger.debug("Retrieved Seasons: $seasons")

        return seasons?.toList() ?: listOf()
    }

    override fun getSeasonsMinimal(showId: String): List<SeasonTrakt> {
        val uri = traktUriProvider.getSeasonsMinimalUri(showId)
        val seasons: Array<SeasonTrakt>? = executeHttpOrMapException { traktRestTemplate.getForObject(uri) }

        logger.debug("Retrieved minimal Seasons: $seasons")

        return seasons?.toList() ?: listOf()
    }

    override fun getShowEpisodes(showId: String): List<EpisodeTrakt> {
        return this.getSeasonsMinimal(showId)
            .map { season -> this.getSeasonEpisodes(showId, season.number ?: "") }
            .flatMap { it.toList() }
    }

    override fun getSeasonEpisodes(showId: String, seasonNumber: String): List<EpisodeTrakt> {
        val uri = traktUriProvider.getSeasonEpisodesUri(showId, seasonNumber)
        val episodes: Array<EpisodeTrakt>? = executeHttpOrMapException { traktRestTemplate.getForObject(uri) }

        logger.debug("Retrieved Season's Episodes: $episodes")

        return episodes?.toList() ?: listOf()
    }

    override fun getSeasonsAsync(showId: String): AsyncResult<List<SeasonTrakt>> =
        AsyncResult(this.getSeasons(showId))

    override fun getShowEpisodesAsync(showId: String): AsyncResult<List<EpisodeTrakt>> =
        AsyncResult(this.getShowEpisodes(showId))

    override fun getUpdates(fromDate: LocalDate): Either<RemoteServiceException, List<ShowUpdateTrakt>> =
        traktUriProvider.getUpdatesUri(fromDate) pipe { uri ->
            Try { traktRestTemplate.getForObject<Array<ShowUpdateTrakt>>(uri) }
                .map { it?.toList() ?: listOf() }
                .toEither()
                .mapLeft { RemoteServiceException(it) }
        }
//
//    override fun getUpdates(fromDate: LocalDate): Either<RemoteServiceException, List<ShowUpdateTrakt>> =
//            traktUriProvider.getUpdatesUri(fromDate).let { uri ->
//                Try { traktRestTemplate.getForObject(uri, Array<ShowUpdateTrakt>::class.java) }
//                        .map { it?.toList() ?: listOf() }
//                        .toEither()
//                        .mapLeft { RemoteServiceException(it) }
//            }

    protected fun <R> executeHttpOrMapException(block: () -> R): R =
        try {
            block()
        } catch (e: HttpClientErrorException) {
            logger.warn("HTTPClient error occurred when contacting Tract.", e)
            throw RemoteServiceException(e)
        }

    protected fun <R> executeHttpOrDefault(block: () -> R, default: (HttpClientErrorException) -> R): R =
        try {
            block()
        } catch (e: HttpClientErrorException) {
            logger.warn("HTTPClient error occurred when contacting Tract.", e)
            default(e)
        }

    protected fun <R> executeHttpOrDefaultOnNotFound(block: () -> R, default: () -> R): R =
        executeHttpOrDefault(block) {
            if (HttpStatus.NOT_FOUND == it.statusCode) {
                default()
            } else {
                throw RemoteServiceException(it)
            }
        }
}
