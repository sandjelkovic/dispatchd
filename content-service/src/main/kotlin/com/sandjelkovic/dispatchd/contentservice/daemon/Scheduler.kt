package com.sandjelkovic.dispatchd.contentservice.daemon

import com.sandjelkovic.dispatchd.contentservice.contentRefreshTaskExecutorBeanName
import com.sandjelkovic.dispatchd.contentservice.service.ContentRefreshService
import mu.KLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * @author sandjelkovic
 * @date 10.2.18.
 */
@Component
class Scheduler(val contentRefreshService: ContentRefreshService) {
    companion object : KLogging()

    @Async(contentRefreshTaskExecutorBeanName)
    @Scheduled(fixedDelayString = "#{\${content.refresh.interval.minutes:1}*1000*60}", initialDelay = (1000 * 5).toLong())
    fun invokeContentRefresh() {
        logger.info("Content refresh started.")
        try {
            val count = contentRefreshService.updateContentIfNeeded()
            logger.info("Content refresh finished. Updated $count shows.", count)
        } catch (e: RuntimeException) {
            logger.info("Exception occurred during content refresh", e)
        }
    }
}
