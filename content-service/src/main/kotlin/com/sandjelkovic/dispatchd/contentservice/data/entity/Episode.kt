package com.sandjelkovic.dispatchd.contentservice.data.entity

import java.time.ZonedDateTime
import javax.persistence.*

/**
 * @author sandjelkovic
 * @date 14.1.18.
 */
@Entity
data class Episode(
        @Id
        @GeneratedValue
        var id: Long?,
        @Version
        var version: Long,
        var airDate: ZonedDateTime?,
        var lastUpdated: ZonedDateTime?,
        var customNumbering: String?,
        @Lob
        var description: String = "",
        var title: String = "",
        var number: Int = 0,
        var seasonNumber: String?,

        var tmdbId: String?,
        var imdbId: String?,
        var traktId: String?,
        var tvdbId: String?,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "seasonId")
        var season: Season?,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "imagesId")
        var imagesGroup: ImagesGroup?,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "showId", nullable = false)
        var tvShow: TvShow?
)
