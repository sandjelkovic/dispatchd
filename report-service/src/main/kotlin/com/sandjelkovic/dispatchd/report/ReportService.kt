package com.sandjelkovic.dispatchd.report

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class ReportService

fun main(args: Array<String>) {
	runApplication<ReportService>(*args)
}
