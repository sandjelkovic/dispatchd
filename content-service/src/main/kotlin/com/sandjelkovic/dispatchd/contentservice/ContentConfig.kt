package com.sandjelkovic.dispatchd.contentservice

import com.sandjelkovic.dispatchd.contentservice.data.repository.ImportStatusRepository
import com.sandjelkovic.dispatchd.contentservice.data.repository.UpdateJobRepository
import com.sandjelkovic.dispatchd.contentservice.service.ImporterSelectionStrategy
import com.sandjelkovic.dispatchd.contentservice.service.ShowImporter
import com.sandjelkovic.dispatchd.contentservice.service.impl.DefaultContentRefreshService
import com.sandjelkovic.dispatchd.contentservice.service.impl.DefaultImportService
import com.sandjelkovic.dispatchd.contentservice.service.impl.DefaultImporterSelectionStrategy
import com.sandjelkovic.dispatchd.contentservice.trakt.interceptor.HeaderRequestInterceptor
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktMediaProvider
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.client.RestTemplate
import java.util.concurrent.Executor


/**
 * @author sandjelkovic
 * @date 26.1.18.
 */
@Configuration
@RefreshScope
@EnableAsync
class ContentConfig(
        @Value("\${content.refresh.interval.minutes:1}")
        var refreshInterval: Int) {

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

    @Bean
    fun contentRefreshService(updateJobRepository: UpdateJobRepository, traktMediaProvider: TraktMediaProvider) = DefaultContentRefreshService(updateJobRepository, traktMediaProvider)

    @Bean
    fun importService(importStatusRepository: ImportStatusRepository, importerSelectionStrategy: ImporterSelectionStrategy) = DefaultImportService(importStatusRepository, importerSelectionStrategy)

    @Bean
    fun importStrategy(showImporters: List<ShowImporter>) = DefaultImporterSelectionStrategy(showImporters)

    @Bean(name = arrayOf("threadPoolTaskExecutor"))
    fun threadPoolTaskExecutor(): Executor {
        return ThreadPoolTaskExecutor()
    }

    @Bean(name = arrayOf(contentRefreshTaskExecutorBeanName))
    fun contentRefreshTaskExecutor(): Executor = ThreadPoolTaskExecutor()
            .apply {
                corePoolSize = 1
                maxPoolSize = 1
            }


    //If the bean is defined this way, it can't be used for @RestClientTest.
//    @Bean
//    fun traktMediaProvider(traktRestTemplate: RestTemplate) = DefaultTraktMediaProvider(traktRestTemplate)
}

const val contentRefreshTaskExecutorBeanName = "contentRefreshTaskExecutor"
