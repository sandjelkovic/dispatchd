package com.sandjelkovic.dispatchd.contentservice.data.entity

import java.time.ZonedDateTime
import javax.persistence.*

/**
 * @author sandjelkovic
 * @date 25.1.18.
 */
@Entity
data class Season(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,
        @Lob
        var description: String = "",
        var number: String? = null,

        var imdbId: String? = null,
        var tmdbId: String? = null,
        var traktId: String? = null,
        var tvdbId: String? = null,

        var episodesAiredCount: Int? = null,
        var airDate: ZonedDateTime? = null,
        var episodesCount: Int = 0,

        @OneToMany(mappedBy = "season")
        var episodes: List<Episode> = listOf(),

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "imagesId")
        var imagesGroup: ImagesGroup? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "showId")
        var show: Show? = null
)
