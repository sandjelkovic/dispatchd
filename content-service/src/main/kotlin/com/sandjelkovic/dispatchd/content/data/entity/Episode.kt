package com.sandjelkovic.dispatchd.content.data.entity

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
    var id: Long? = null,
    @Version
    var version: Long? = null,
    var airDate: ZonedDateTime? = null,
    var lastUpdated: ZonedDateTime? = null,
    var customNumbering: String? = null,
    @Lob
    var description: String = "",
    var title: String = "",
    var number: Int = 0,
    var seasonNumber: String? = null,

    @Column(unique = true)
    var imdbId: String? = null,
    @Column(unique = true)
    var tmdbId: String? = null,
    @Column(unique = true)
    var traktId: String? = null,
    @Column(unique = true)
    var tvdbId: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seasonId")
    var season: Season? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imagesId")
    var imagesGroup: ImagesGroup? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showId")
    var show: Show? = null
)
