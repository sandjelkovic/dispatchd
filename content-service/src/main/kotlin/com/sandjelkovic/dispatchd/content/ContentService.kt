package com.sandjelkovic.dispatchd.content

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * @author sandjelkovic
 * @date 14.1.18.
 */

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableDiscoveryClient
class ContentService

fun main(args: Array<String>) {
    runApplication<ContentService>(*args)
}
