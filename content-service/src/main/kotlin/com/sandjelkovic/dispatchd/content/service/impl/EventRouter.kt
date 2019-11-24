package com.sandjelkovic.dispatchd.content.service.impl

import com.sandjelkovic.dispatchd.content.event.ApiEvent
import com.sandjelkovic.dispatchd.content.event.JobReportCreated
import com.sandjelkovic.dispatchd.content.event.ShowImported
import org.springframework.context.event.EventListener


class EventRouter {
    @EventListener
    fun routeApiEvent(event: ApiEvent) {
        val message = when (event) {
            is JobReportCreated -> "New Job Report has been created ${event.updateJob}"
            is ShowImported -> "Show with the id of ${event.showId} has been imported"
        }
        println(message)
    }
}
