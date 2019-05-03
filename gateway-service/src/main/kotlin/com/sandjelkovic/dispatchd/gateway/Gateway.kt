package com.sandjelkovic.dispatchd.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Gateway {
    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator = builder
        .routes()
        .route("monitor-service") { spec ->
            spec.path("/monitor-service/**")
                .filters { it.stripPrefix(1) }
                .uri("lb://monitor-service")
        }
        .route("content-service") { spec ->
            spec.path("/content-service/**")
                .filters { it.stripPrefix(1) }
                .uri("lb://content-service")
        }
        .build()
}

fun main(args: Array<String>) {
    runApplication<Gateway>(*args)
}
