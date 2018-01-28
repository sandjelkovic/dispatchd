package com.sandjelkovic.dispatchd.contentservice.trakt.dto.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
data class ShowUpdateTrakt(
        @JsonProperty("updated_at")
        val updatedAt: ZonedDateTime? = null,
        val show: ShowTrakt? = null
)
