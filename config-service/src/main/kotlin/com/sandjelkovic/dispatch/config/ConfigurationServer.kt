package com.sandjelkovic.dispatch.config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.config.server.EnableConfigServer

@SpringBootApplication
@EnableConfigServer
class ConfigurationServer

fun main(args: Array<String>) {
    runApplication<ConfigurationServer>(*args)
}
