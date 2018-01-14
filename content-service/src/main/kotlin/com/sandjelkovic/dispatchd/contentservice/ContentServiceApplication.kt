package com.sandjelkovic.dispatchd.contentservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @author sandjelkovic
 * @date 14.1.18.
 */
@SpringBootApplication

class ContentServiceApplication

fun main(args: Array<String>) {
    runApplication<ContentServiceApplication>(*args)
}
