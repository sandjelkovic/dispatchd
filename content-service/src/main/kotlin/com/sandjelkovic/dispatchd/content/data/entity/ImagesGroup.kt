package com.sandjelkovic.dispatchd.content.data.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * @author sandjelkovic
 * @date 25.1.18.
 */
@Entity
data class ImagesGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,
    var fanartFull: String?,
    var fanartMedium: String?,
    var fanartThumb: String?,
    var fantartSmall: String?,
    var posterFull: String?,
    var posterMedium: String?,
    var posterSmall: String?,
    var posterThumb: String?
)
