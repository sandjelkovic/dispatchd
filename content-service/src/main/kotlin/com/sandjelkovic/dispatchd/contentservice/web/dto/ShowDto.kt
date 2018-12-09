package com.sandjelkovic.dispatchd.contentservice.web.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 2018-12-08
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class ShowDto(
    val id: Long,
    val title: String = "",
    val description: String = "",
    val year: Int? = null,
    val status: String,
    val lastUpdatedAt: ZonedDateTime,
    val imdbId: String? = null,
    val tmdbId: String? = null,
    val traktId: String? = null,
    val tvdbId: String? = null
)

fun Show.toDto() = ShowDto(
    id = id!!,
    title = title,
    description = description,
    year = year,
    status = status,
    lastUpdatedAt = lastLocalUpdate,
    imdbId = imdbId,
    tmdbId = tmdbId,
    traktId = traktId,
    tvdbId = tvdbId
)
