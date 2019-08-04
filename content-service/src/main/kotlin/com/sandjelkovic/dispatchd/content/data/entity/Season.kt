package com.sandjelkovic.dispatchd.content.data.entity

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

    @Column(unique = true)
    var imdbId: String? = null,
    @Column(unique = true)
    var tmdbId: String? = null,
    @Column(unique = true)
    var traktId: String? = null,
    @Column(unique = true)
    var tvdbId: String? = null,

    var episodesAiredCount: Int? = null,
    var airDate: ZonedDateTime? = null,
    var episodesCount: Int = 0,

    @OneToMany(mappedBy = "season")
    var episodes: List<Episode> = mutableListOf(), // JPA/Hibernate Specifics

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imagesId")
    var imagesGroup: ImagesGroup? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showId")
    var show: Show? = null
)
