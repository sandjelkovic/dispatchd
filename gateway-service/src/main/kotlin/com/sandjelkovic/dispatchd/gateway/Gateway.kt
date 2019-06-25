package com.sandjelkovic.dispatchd.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Gateway {
    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator = builder
        .routes {
            route(id = "monitor-service") {
                path("/monitor-service/**")
                filters {
                    stripPrefix(1)
                }
                uri("lb://monitor-service")
            }
            route(id = "content-service") {
                path("/content-service/**")
                filters {
                    stripPrefix(1)
                }
                uri("lb://content-service")
            }
            route {
                path("/uaa/**")
                uri("lb://auth-service/")
            }
        }
}

fun main(args: Array<String>) {
    runApplication<Gateway>(*args)
}
