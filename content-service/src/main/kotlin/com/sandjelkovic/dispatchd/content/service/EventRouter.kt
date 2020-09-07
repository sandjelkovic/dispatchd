package com.sandjelkovic.dispatchd.content.service

import com.sandjelkovic.dispatchd.content.api.*
import org.springframework.context.event.EventListener


class EventRouter {
    @EventListener
    fun routeApiEvent(event: ApiEvent) {
        val message = when (event) {
            is JobReportCreated -> "New Job Report has been created ${event.updateJob}"
            is ShowCreated -> "Show with the id of ${event.showId} has been created"
            is ShowUpdated -> "Show with the id of ${event.showId} has been updated"
            is ShowUpdateFailed -> "Update of the show with the id of ${event.showId} has failed"
        }
        println(message)
    }
}
