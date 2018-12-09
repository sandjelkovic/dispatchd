package com.sandjelkovic.dispatchd.contentservice.web.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 2018-12-09
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class EpisodeDTO {

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

    fun id(id: Long?): EpisodeDTO {
        this.id = id
        return this
    }

    fun airdate(airdate: ZonedDateTime): EpisodeDTO {
        this.airdate = airdate
        return this
    }

    fun lastUpdated(lastUpdated: ZonedDateTime): EpisodeDTO {
        this.lastUpdated = lastUpdated
        return this
    }

    fun customNumbering(customNumbering: String): EpisodeDTO {
        this.customNumbering = customNumbering
        return this
    }

    fun description(description: String): EpisodeDTO {
        this.description = description
        return this
    }

    fun number(number: Int?): EpisodeDTO {
        this.number = number
        return this
    }

    fun seasonNumber(seasonNumber: String): EpisodeDTO {
        this.seasonNumber = seasonNumber
        return this
    }

    fun title(title: String): EpisodeDTO {
        this.title = title
        return this
    }

    fun imdbId(imdbId: String): EpisodeDTO {
        this.imdbId = imdbId
        return this
    }

    fun tmdbId(tmdbId: String): EpisodeDTO {
        this.tmdbId = tmdbId
        return this
    }

    fun traktId(traktId: String): EpisodeDTO {
        this.traktId = traktId
        return this
    }

    fun tvdbId(tvdbId: String): EpisodeDTO {
        this.tvdbId = tvdbId
        return this
    }

    fun seasonId(seasonId: Long?): EpisodeDTO {
        this.seasonId = seasonId
        return this
    }

    fun tvShowId(tvShowId: Long?): EpisodeDTO {
        this.tvShowId = tvShowId
        return this
    }
}
