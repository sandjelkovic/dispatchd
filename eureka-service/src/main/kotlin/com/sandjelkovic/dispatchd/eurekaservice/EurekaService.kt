package com.sandjelkovic.dispatchd.eurekaservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@SpringBootApplication
@EnableEurekaServer
class EurekaService

fun main(args: Array<String>) {
    runApplication<EurekaService>(*args)
}
