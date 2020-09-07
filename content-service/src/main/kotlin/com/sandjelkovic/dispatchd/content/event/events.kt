package com.sandjelkovic.dispatchd.content.event

import com.sandjelkovic.dispatchd.content.data.entity.UpdateJob

sealed class ApiEvent
data class JobReportCreated(val updateJob: UpdateJob) : ApiEvent()

// TODO Put data in events, do not rely on querying
data class ShowCreated(val showId: String) : ApiEvent()
data class ShowUpdated(val showId: String) : ApiEvent()
data class ShowUpdateFailed(val showId: String) : ApiEvent()
