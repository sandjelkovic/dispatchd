package com.sandjelkovic.dispatchd.contentservice

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

/**
 * @author sandjelkovic
 * @date 26.1.18.
 */
@Configuration
class ContentConfig {
    @Value("\${content.refresh.interval.minutes:1}")
    var refreshInterval: Int = 0
}
