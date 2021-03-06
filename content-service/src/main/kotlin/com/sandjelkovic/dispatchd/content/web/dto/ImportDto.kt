package com.sandjelkovic.dispatchd.content.web.dto

import com.sandjelkovic.dispatchd.content.data.entity.ImportProgressStatus
import com.sandjelkovic.dispatchd.content.data.entity.ImportStatus
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 24.6.18.
 */
data class ImportDto(val mediaUrl: String)

data class ImportStatusWebDto(
    val statusId: Long,
    val mediaUrl: String,
    val initiationTime: ZonedDateTime,
    val finishTime: ZonedDateTime?,
    val status: ImportProgressStatus
)

fun ImportStatus.toWebDto() = ImportStatusWebDto(
    statusId = id!!,
    mediaUrl = mediaUrl,
    initiationTime = initiationTime,
    finishTime = finishTime,
    status = status
)

