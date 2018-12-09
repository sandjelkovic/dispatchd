package com.sandjelkovic.dispatchd.contentservice.web.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.sandjelkovic.dispatchd.contentservice.data.entity.Season
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 2018-12-09
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class SeasonDto(
    val id: Long,
    val description: String = "",
    val number: String? = null,
    val imdbId: String? = null,
    val tmdbId: String? = null,
    val traktId: String? = null,
    val tvdbId: String? = null,
    val episodesAiredCount: Int = 0,
    val airDate: ZonedDateTime? = null,
    val episodesCount: Int = 0
)

fun Season.toDto() = SeasonDto(
    id = id!!,
    description = description,
    number = number,
    episodesAiredCount = episodesAiredCount ?: 0,
    episodesCount = episodesCount,
    airDate = airDate,
    imdbId = imdbId,
    tmdbId = tmdbId,
    traktId = traktId,
    tvdbId = tvdbId
)
