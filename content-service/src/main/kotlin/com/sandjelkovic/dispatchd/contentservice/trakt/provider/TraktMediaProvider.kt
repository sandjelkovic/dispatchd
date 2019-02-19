package com.sandjelkovic.dispatchd.contentservice.trakt.provider

import arrow.core.Either
import arrow.core.Option
import arrow.core.Try
import com.sandjelkovic.dispatchd.contentservice.service.RemoteServiceException
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.EpisodeTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.SeasonTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowUpdateTrakt
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import java.time.LocalDate

/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
interface TraktMediaProvider {
    fun getShow(showId: String): Try<Option<ShowTrakt>>
    fun getShowEpisodes(showId: String): Try<List<EpisodeTrakt>>
    fun getSeasons(showId: String): Try<List<SeasonTrakt>>
    fun getSeasonsMinimal(showId: String): Try<List<SeasonTrakt>>
    fun getSeasonEpisodes(showId: String, seasonNumber: String): Try<List<EpisodeTrakt>>
    fun getUpdates(fromDate: LocalDate): Either<RemoteServiceException, List<ShowUpdateTrakt>>

    @Async
    fun getSeasonsAsync(showId: String): AsyncResult<Try<List<SeasonTrakt>>>

    @Async
    fun getShowEpisodesAsync(showId: String): AsyncResult<Try<List<EpisodeTrakt>>>
}
