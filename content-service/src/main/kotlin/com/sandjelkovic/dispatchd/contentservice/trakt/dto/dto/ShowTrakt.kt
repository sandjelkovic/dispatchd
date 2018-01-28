package com.sandjelkovic.dispatchd.contentservice.trakt.dto.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
data class ShowTrakt(
        val title: String? = null,
        val year: Int? = null,
        val status: String? = null,
        @JsonProperty("updated_at")
        val updatedAt: ZonedDateTime? = null,
        val overview: String? = null,
        val ids: Map<String, String> = mapOf()
)
