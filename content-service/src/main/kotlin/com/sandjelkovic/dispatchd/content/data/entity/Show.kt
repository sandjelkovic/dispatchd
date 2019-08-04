package com.sandjelkovic.dispatchd.content.data.entity

import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.Lob
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

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
    var status: String = "",
    var lastLocalUpdate: ZonedDateTime = ZonedDateTime.now().minusYears(500),

    @Column(unique = true)
    var imdbId: String? = null,
    @Column(unique = true)
    var tmdbId: String? = null,
    @Column(unique = true)
    var traktId: String? = null,
    @Column(unique = true)
    var traktSlug: String? = null,
    @Column(unique = true)
    var tvdbId: String? = null,

    @OneToMany(mappedBy = "show")
    var episodes: List<Episode> = mutableListOf(), // JPA/Hibernate Specifics

    @OneToMany(mappedBy = "show")
    var seasons: List<Season> = mutableListOf(), // JPA/Hibernate Specifics

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imagesId")
    var imagesGroup: ImagesGroup? = null
)
