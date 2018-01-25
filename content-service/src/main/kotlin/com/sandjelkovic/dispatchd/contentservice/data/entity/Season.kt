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
        var id: Long?,
        @Lob
        var description: String = "",
        var number: String?,

        var imdbId: String?,
        var tmdbId: String?,
        var traktId: String?,
        var tvdbId: String?,

        var episodesAiredCount: Int?,
        var airDate: ZonedDateTime?,
        var episodesCount: Int = 0,

        @OneToMany(mappedBy = "season")
        var episodes: List<Episode> = listOf(),

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "imagesId", nullable = true)
        var imagesGroup: ImagesGroup?,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "TvShowId", nullable = false)
        var tvShow: TvShow?
)
