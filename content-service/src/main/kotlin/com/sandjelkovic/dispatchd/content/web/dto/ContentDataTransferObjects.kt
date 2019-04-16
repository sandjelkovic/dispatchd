package com.sandjelkovic.dispatchd.content.web.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.sandjelkovic.dispatchd.content.data.entity.Episode
import com.sandjelkovic.dispatchd.content.data.entity.Season
import com.sandjelkovic.dispatchd.content.data.entity.Show
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

@JsonIgnoreProperties(ignoreUnknown = true)
data class EpisodeDto(
    val id: Long? = null,
    val airDate: ZonedDateTime? = null,
    val lastUpdated: ZonedDateTime,
    val customNumbering: String? = null,
    val description: String = "",
    val number: Int = 0,
    val seasonNumber: String = "",
    val title: String? = null,
    val imdbId: String? = null,
    val tmdbId: String? = null,
    val traktId: String? = null,
    val tvdbId: String? = null,
    @JsonIgnore
    val seasonId: Long,
    @JsonIgnore
    val showId: Long
)

fun Episode.toDto() = EpisodeDto(
    id = id,
    airDate = airDate,
    lastUpdated = lastUpdated ?: ZonedDateTime.now().minusYears(1000),
    customNumbering = customNumbering,
    description = description,
    title = title,
    number = number,
    seasonNumber = seasonNumber ?: "",
    imdbId = imdbId,
    tmdbId = tmdbId,
    traktId = traktId,
    tvdbId = tvdbId,
    seasonId = season?.id!!,
    showId = show?.id!!
)
