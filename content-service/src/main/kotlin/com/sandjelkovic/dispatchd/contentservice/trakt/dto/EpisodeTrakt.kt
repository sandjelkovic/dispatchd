package com.sandjelkovic.dispatchd.contentservice.trakt.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
data class EpisodeTrakt(
        val season: String? = null,
        val number: Int? = null,
        val title: String? = null,
        val ids: Map<String, String> = mapOf(),
        val overview: String? = null,
        @JsonProperty("updated_at")
        val updatedAt: ZonedDateTime? = null,
        @JsonProperty("first_aired")
        val firstAired: Instant? = null
)
