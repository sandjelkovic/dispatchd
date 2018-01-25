package com.sandjelkovic.dispatchd.contentservice.data.entity

import java.time.ZonedDateTime
import javax.persistence.*

/**
 * @author sandjelkovic
 * @date 25.1.18.
 */
@Entity
data class TvShow(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long?,
        var title: String = "",
        @Lob
        var description: String = "",
        var year: Int?,
        var status: String?,
        var lastLocalUpdate: ZonedDateTime?,

        var imdbId: String?,
        var tmdbId: String?,
        var traktId: String?,
        var tvdbId: String?,

        @OneToMany(mappedBy = "tvShow")
        var episodes: List<Episode> = listOf(),

        @OneToMany(mappedBy = "tvShow")
        var seasons: List<Season> = listOf(),

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "imagesId", nullable = true)
        var imagesGroup: ImagesGroup?
)
