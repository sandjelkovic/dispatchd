package com.sandjelkovic.dispatchd.contentservice.trakt.provider

import arrow.core.Either
import com.sandjelkovic.dispatchd.contentservice.service.RemoteServiceException
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.EpisodeTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.SeasonTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowUpdateTrakt
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import java.time.LocalDate
import java.util.*

/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
interface TraktMediaProvider {
    fun getShow(showId: String): Optional<ShowTrakt>
    fun getShowEpisodes(showId: String): List<EpisodeTrakt>
    fun getSeasons(showId: String): List<SeasonTrakt>
    fun getSeasonsMinimal(showId: String): List<SeasonTrakt>
    fun getSeasonEpisodes(showId: String, seasonNumber: String): List<EpisodeTrakt>
    fun getUpdates(fromDate: LocalDate): Either<RemoteServiceException, List<ShowUpdateTrakt>>

    @Async
    fun getSeasonsAsync(showId: String): AsyncResult<List<SeasonTrakt>>

    @Async
    fun getShowEpisodesAsync(showId: String): AsyncResult<List<EpisodeTrakt>>
}
