package com.sandjelkovic.dispatchd.content.trakt.provider.impl

import arrow.core.*
import arrow.core.extensions.`try`.applicativeError.handleErrorWith
import com.sandjelkovic.dispatchd.content.trakt.dto.EpisodeTrakt
import com.sandjelkovic.dispatchd.content.trakt.dto.SeasonTrakt
import com.sandjelkovic.dispatchd.content.trakt.dto.ShowTrakt
import com.sandjelkovic.dispatchd.content.trakt.dto.ShowUpdateTrakt
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktMediaProvider
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktUriProvider
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

        return httpGetOption { traktRestTemplate.getForObject<ShowTrakt?>(uri) }
    }

    override fun getSeasons(showId: String): Try<List<SeasonTrakt>> {
        val uri = traktUriProvider.getSeasonsUri(showId)

        return httpGetList { traktRestTemplate.getForObject<Array<SeasonTrakt?>>(uri).toList() }
            .map { it.filterNotNull() }
    }

    override fun getSeasonsMinimal(showId: String): Try<List<SeasonTrakt>> {
        val uri = traktUriProvider.getSeasonsMinimalUri(showId)

        return httpGetList { traktRestTemplate.getForObject<Array<SeasonTrakt?>>(uri).toList() }
            .map { it.filterNotNull() }
    }

    override fun getShowEpisodes(showId: String): Try<List<EpisodeTrakt>> {
        return getSeasonsMinimal(showId)
            .flatMap { seasons ->
                // TODO parallelise, optimise unneeded calls in case one fails
                val attemptedEpisodesLists = seasons.map { season -> getSeasonEpisodes(showId, season.number ?: "") }
                when {
                    hasFailures(attemptedEpisodesLists) -> getFirstFailure(attemptedEpisodesLists)
                    else -> flattenSuccesses(attemptedEpisodesLists).success()
                }
            }
    }

    override fun getSeasonEpisodes(showId: String, seasonNumber: String): Try<List<EpisodeTrakt>> {
        val uri = traktUriProvider.getSeasonEpisodesUri(showId, seasonNumber)
        return httpGetList { traktRestTemplate.getForObject<Array<EpisodeTrakt?>>(uri).toList() }
            .map { it.filterNotNull() }
    }

    override fun getSeasonsAsync(showId: String): AsyncResult<Try<List<SeasonTrakt>>> =
        AsyncResult(this.getSeasons(showId))

    override fun getShowEpisodesAsync(showId: String): AsyncResult<Try<List<EpisodeTrakt>>> =
        AsyncResult(this.getShowEpisodes(showId))

    override fun getUpdates(fromDate: LocalDate): Try<List<ShowUpdateTrakt>> {
        val uri = traktUriProvider.getUpdatesUri(fromDate)

        return Try { traktRestTemplate.getForObject<Array<ShowUpdateTrakt?>>(uri) }
            .map { it.toList() }
            .map { it.filterNotNull() }
    }

    private fun flattenSuccesses(attemptedEpisodeLists: List<Try<List<EpisodeTrakt>>>) =
        attemptedEpisodeLists.map { it as Success }
            .map { it.value }
            .flatten()

    private fun getFirstFailure(attemptedEpisodeLists: List<Try<List<EpisodeTrakt>>>) =
        attemptedEpisodeLists.first { it is Failure } as Failure

    private fun hasFailures(attemptedEpisodeLists: List<Try<List<EpisodeTrakt>>>) =
        attemptedEpisodeLists.any { it is Failure }

    private fun isHttpClientErrorAndNotFound(it: Throwable) = it is HttpClientErrorException && isNotFound(it)

    private fun isNotFound(it: HttpClientErrorException) = HttpStatus.NOT_FOUND == it.statusCode

    private fun <T> partialRecovery(
        predicate: (Throwable) -> Boolean,
        success: (Throwable) -> Success<T>
    ): (Throwable) -> Try<T> =
        { throwable: Throwable ->
            when {
                predicate(throwable) -> success(throwable)
                else -> Failure(throwable)
            }
        }

    private fun <T> httpGetList(block: () -> List<T>?): Try<List<T>> =
        Try(f = block)
            .map { it?.toList() ?: listOf() }
            .handleErrorWith(
                partialRecovery(::isHttpClientErrorAndNotFound) { Success(listOf<T>()) }
            )

    private fun <T> httpGetOption(block: () -> T?): Try<Option<T>> =
        Try(f = block)
            .map { Option.fromNullable(it) }
            .handleErrorWith(
                partialRecovery(::isHttpClientErrorAndNotFound) { Success(Option.empty<T>()) }
            )
}
