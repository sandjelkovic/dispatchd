package com.sandjelkovic.dispatchd.content.trakt.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
data class SeasonTrakt(
        val number: String? = null,
        val ids: Map<String, String> = mapOf(),
        val overview: String? = null,
        @JsonProperty("first_aired")
        val firstAired: ZonedDateTime? = null,
        @JsonProperty("episode_count")
        val episodeCount: Int? = null,
        @JsonProperty("aired_episodes")
        val airedEpisodes: Int? = null
)
