package com.sandjelkovic.dispatchd.contentservice.web.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.sandjelkovic.dispatchd.contentservice.data.entity.Episode
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 2018-12-09
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class EpisodeDto {

    private var id: Long? = null

    private var airdate: ZonedDateTime? = null

    @JsonIgnore
    private var lastUpdated: ZonedDateTime? = null

    private var customNumbering: String? = null

    private var description: String? = null

    private var number: Int? = null

    private var seasonNumber: String? = null

    private var title: String? = null

    private var imdbId: String? = null

    private var tmdbId: String? = null

    private var traktId: String? = null

    private var tvdbId: String? = null

    private var seasonId: Long? = null

    private var tvShowId: Long? = null

    fun id(id: Long?): EpisodeDto {
        this.id = id
        return this
    }

    fun airdate(airdate: ZonedDateTime): EpisodeDto {
        this.airdate = airdate
        return this
    }

    fun lastUpdated(lastUpdated: ZonedDateTime): EpisodeDto {
        this.lastUpdated = lastUpdated
        return this
    }

    fun customNumbering(customNumbering: String): EpisodeDto {
        this.customNumbering = customNumbering
        return this
    }

    fun description(description: String): EpisodeDto {
        this.description = description
        return this
    }

    fun number(number: Int?): EpisodeDto {
        this.number = number
        return this
    }

    fun seasonNumber(seasonNumber: String): EpisodeDto {
        this.seasonNumber = seasonNumber
        return this
    }

    fun title(title: String): EpisodeDto {
        this.title = title
        return this
    }

    fun imdbId(imdbId: String): EpisodeDto {
        this.imdbId = imdbId
        return this
    }

    fun tmdbId(tmdbId: String): EpisodeDto {
        this.tmdbId = tmdbId
        return this
    }

    fun traktId(traktId: String): EpisodeDto {
        this.traktId = traktId
        return this
    }

    fun tvdbId(tvdbId: String): EpisodeDto {
        this.tvdbId = tvdbId
        return this
    }

    fun seasonId(seasonId: Long?): EpisodeDto {
        this.seasonId = seasonId
        return this
    }

    fun tvShowId(tvShowId: Long?): EpisodeDto {
        this.tvShowId = tvShowId
        return this
    }
}

fun Episode.toDto() = Episode(
    id = id,
    version = version,
    airDate = airDate,
    lastUpdated = lastUpdated,
    customNumbering = customNumbering,
    description = description,
    title = title,
    number = number,
    seasonNumber = seasonNumber,
    imdbId = imdbId,
    tmdbId = tmdbId,
    traktId = traktId,
    tvdbId = tvdbId,
    imagesGroup = imagesGroup
)
