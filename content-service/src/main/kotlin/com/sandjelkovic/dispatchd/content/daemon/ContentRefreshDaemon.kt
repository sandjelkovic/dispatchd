package com.sandjelkovic.dispatchd.content.daemon

import com.sandjelkovic.dispatchd.content.contentRefreshTaskExecutorBeanName
import com.sandjelkovic.dispatchd.content.service.ContentRefreshService
import mu.KLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled

/**
 * @author sandjelkovic
 * @date 10.2.18.
 */
open class ContentRefreshDaemon(private val contentRefreshService: ContentRefreshService) {
    companion object : KLogging()

    @Async(contentRefreshTaskExecutorBeanName)
    @Scheduled(fixedDelayString = "#{\${content.refresh.interval.minutes:1}*1000*60}", initialDelay = (1000 * 5).toLong())
    open fun invokeContentRefresh() {
        logger.info("Content refresh started.")
        try {
            contentRefreshService.updateContentIfStale()
                .fold(
                    { logger.error("Exception occurred during content refresh", it) },
                    { logger.info("Content refresh finished. Updated ${it.size} shows.") }
                )
        } catch (e: RuntimeException) {
            logger.error("Exception occurred during content refresh", e)
        }
    }
}
