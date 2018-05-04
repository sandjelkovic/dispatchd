package com.sandjelkovic.dispatchd.contentservice

import com.sandjelkovic.dispatchd.contentservice.converter.Trakt2EpisodeConverter
import com.sandjelkovic.dispatchd.contentservice.converter.Trakt2SeasonConverter
import com.sandjelkovic.dispatchd.contentservice.converter.Trakt2ShowConverter
import com.sandjelkovic.dispatchd.contentservice.data.repository.*
import com.sandjelkovic.dispatchd.contentservice.interceptor.HeaderRequestInterceptor
import com.sandjelkovic.dispatchd.contentservice.service.impl.DefaultContentRefreshService
import com.sandjelkovic.dispatchd.contentservice.service.impl.DefaultImportService
import com.sandjelkovic.dispatchd.contentservice.service.impl.DefaultTraktImporter
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktMediaProvider
import mu.KLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.ConversionService
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

    @Bean
    fun contentRefreshService(updateJobRepository: UpdateJobRepository, traktMediaProvider: TraktMediaProvider) = DefaultContentRefreshService(updateJobRepository, traktMediaProvider)

    @Bean
    fun importService(importStatusRepository: ImportStatusRepository) = DefaultImportService(importStatusRepository)

    @Bean
    fun traktImporter(showRepository: ShowRepository, seasonRepository: SeasonRepository,
                      episodeRepository: EpisodeRepository, @Qualifier("mvcConversionService") conversionService: ConversionService, provider: TraktMediaProvider) =
            DefaultTraktImporter(showRepository, seasonRepository, episodeRepository, conversionService, provider)

    @Bean
    fun trakt2EpisodeConverter() = Trakt2EpisodeConverter()

    @Bean
    fun trakt2SeasonConverter() = Trakt2SeasonConverter()

    @Bean
    fun trakt2ShowConverter() = Trakt2ShowConverter()

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
