package com.sandjelkovic.dispatchd.contentservice.trakt.provider

import com.sandjelkovic.dispatchd.contentservice.trakt.dto.EpisodeTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.SeasonTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowTrakt
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
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

    @Async
    fun getSeasonsAsync(showId: String): AsyncResult<List<SeasonTrakt>>

    @Async
    fun getShowEpisodesAsync(showId: String): AsyncResult<List<EpisodeTrakt>>
}
