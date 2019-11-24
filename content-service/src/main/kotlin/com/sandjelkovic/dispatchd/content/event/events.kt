package com.sandjelkovic.dispatchd.content.event

import com.sandjelkovic.dispatchd.content.data.entity.UpdateJob

sealed class ApiEvent
data class JobReportCreated(val updateJob: UpdateJob) : ApiEvent()
data class ShowImported(val showId: Long) : ApiEvent()
