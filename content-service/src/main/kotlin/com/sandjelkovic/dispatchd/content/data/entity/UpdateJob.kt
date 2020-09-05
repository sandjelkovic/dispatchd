package com.sandjelkovic.dispatchd.content.data.entity

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

/**
 * @author sandjelkovic
 * @date 3.3.18.
 */
@Entity
data class UpdateJob(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(nullable = false)
    @NotNull
    var finishTime: ZonedDateTime = ZonedDateTime.of(LocalDateTime.MIN, ZoneId.systemDefault()),
    @Column(nullable = false)
    @NotNull
    var startTime: ZonedDateTime = ZonedDateTime.of(LocalDateTime.MIN, ZoneId.systemDefault()),
    @NotNull
    var success: Boolean = false
)
