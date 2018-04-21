package com.sandjelkovic.dispatchd.contentservice.data.entity

import org.hibernate.validator.constraints.Length
import java.time.ZonedDateTime
import javax.persistence.*

/**
 * @author sandjelkovic
 * @date 21.4.18.
 */
@Entity
data class ImportStatus(
        @Id
        @GeneratedValue
        var id: Long? = null,
        @Column(nullable = false, length = 1000)
        @Length(max = 1000)
        var mediaUrl: String = "",

        @Column(nullable = false)
        var initiationTime: ZonedDateTime = ZonedDateTime.now(),

        var finishTime: ZonedDateTime? = null,

        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        var status: ImportProgressStatus = ImportProgressStatus.QUEUED
)
