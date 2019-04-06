package com.sandjelkovic.dispatchd.report

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class ReportServiceApplication

fun main(args: Array<String>) {
	runApplication<ReportServiceApplication>(*args)
}
