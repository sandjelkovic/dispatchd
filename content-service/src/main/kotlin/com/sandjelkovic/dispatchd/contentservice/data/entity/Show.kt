package com.sandjelkovic.dispatchd.contentservice.data.entity

import java.time.ZonedDateTime
import javax.persistence.*

/**
 * @author sandjelkovic
 * @date 25.1.18.
 */
@Entity
data class Show(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,
        var title: String = "",
        @Lob
        var description: String = "",
        var year: Int? = null,
        var status: String? = null,
        var lastLocalUpdate: ZonedDateTime? = null,

        @Column(unique = true)
        var imdbId: String? = null,
        @Column(unique = true)
        var tmdbId: String? = null,
        @Column(unique = true)
        var traktId: String? = null,
        @Column(unique = true)
        var tvdbId: String? = null,

        @OneToMany(mappedBy = "show")
        var episodes: List<Episode> = listOf(),

        @OneToMany(mappedBy = "show")
        var seasons: List<Season> = listOf(),

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "imagesId")
        var imagesGroup: ImagesGroup? = null
)
