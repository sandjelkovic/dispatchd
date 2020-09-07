package com.sandjelkovic.dispatchd.content.trakt.provider

import arrow.core.Option
import arrow.core.Try
import com.sandjelkovic.dispatchd.content.trakt.dto.EpisodeTrakt
import com.sandjelkovic.dispatchd.content.trakt.dto.SeasonTrakt
import com.sandjelkovic.dispatchd.content.trakt.dto.ShowTrakt
import com.sandjelkovic.dispatchd.content.trakt.dto.ShowUpdateTrakt
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
    fun getUpdates(fromDate: LocalDate): Try<List<ShowUpdateTrakt>>

    //TODO convert to coroutines or Arrow.IO
    @Async
    fun getSeasonsAsync(showId: String): AsyncResult<Try<List<SeasonTrakt>>>

    @Async
    fun getShowEpisodesAsync(showId: String): AsyncResult<Try<List<EpisodeTrakt>>>
}
