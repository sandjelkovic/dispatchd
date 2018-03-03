package com.sandjelkovic.dispatchd.contentservice.data.entity

import java.time.Instant
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
        var finishTime: ZonedDateTime = ZonedDateTime.from(Instant.MIN),
        @NotNull
        var isSuccess: Boolean = false
)
