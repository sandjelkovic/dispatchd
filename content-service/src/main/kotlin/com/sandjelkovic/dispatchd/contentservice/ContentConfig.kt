package com.sandjelkovic.dispatchd.contentservice

import com.sandjelkovic.dispatchd.contentservice.interceptor.HeaderRequestInterceptor
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.impl.DefaultTraktMediaProvider
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

/**
 * @author sandjelkovic
 * @date 26.1.18.
 */
@Configuration
@RefreshScope
class ContentConfig(
        @Value("\${content.refresh.interval.minutes:1}")
        var refreshInterval: Int,
        @Value("\${trakt.baseServiceUrl: }")
        var baseServiceUrl: String,
        @Value("\${trakt.appSecret: }")
        var appSecret: String) {

    companion object : KLogging()

    @Bean
    @RefreshScope
    fun traktRestTemplate(
            @Value("\${trakt.appId: }") appId: String,
            @Value("\${trakt.apiVersion: }") apiVersion: String): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.interceptors.add(HeaderRequestInterceptor("Content-type", "application/json"))
        restTemplate.interceptors.add(HeaderRequestInterceptor("trakt-api-key", appId))
        restTemplate.interceptors.add(HeaderRequestInterceptor("trakt-api-version", apiVersion))
        logger.debug("Configured TraktRestTemplate with api key: ${appId} and api version: ${apiVersion}")
        return restTemplate
    }

    //If the bean is defined this way, it can't be used for @RestClientTest.
    @Bean
    fun traktMediaProvider(traktRestTemplate: RestTemplate) = DefaultTraktMediaProvider(traktRestTemplate)
}
