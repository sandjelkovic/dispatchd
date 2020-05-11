package com.sandjelkovic.dispatchd.content

import com.sandjelkovic.dispatchd.content.daemon.ContentRefreshDaemon
import com.sandjelkovic.dispatchd.content.data.repository.ImportStatusRepository
import com.sandjelkovic.dispatchd.content.data.repository.ShowRepository
import com.sandjelkovic.dispatchd.content.data.repository.UpdateJobRepository
import com.sandjelkovic.dispatchd.content.service.ContentRefreshService
import com.sandjelkovic.dispatchd.content.service.ImporterSelectionStrategy
import com.sandjelkovic.dispatchd.content.service.ShowImporter
import com.sandjelkovic.dispatchd.content.service.impl.*
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktMediaProvider
import com.sandjelkovic.dispatchd.content.trakt.service.TraktShowImporter
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ApplicationEventMulticaster
import org.springframework.context.event.SimpleApplicationEventMulticaster
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor


/**
 * @author sandjelkovic
 * @date 26.1.18.
 */
@Configuration
@RefreshScope
@EnableAsync
@ConfigurationPropertiesScan
class ContentConfig(
    @Value("\${content.refresh.interval.minutes:1}")
    var refreshInterval: Int
) {

    companion object : KLogging()

    @Bean
    fun contentRefreshService(
        updateJobRepository: UpdateJobRepository,
        traktMediaProvider: TraktMediaProvider,
        showRepository: ShowRepository,
        traktShowImporter: TraktShowImporter,
        eventPublisher: ApplicationEventPublisher
    ) =
        DefaultContentRefreshService(updateJobRepository, traktMediaProvider, showRepository, traktShowImporter, eventPublisher)

    @Bean
    fun importService(importStatusRepository: ImportStatusRepository, importerSelectionStrategy: ImporterSelectionStrategy, asyncService: SpringAsyncService) =
        DefaultImportService(importStatusRepository, importerSelectionStrategy, asyncService)

    @Bean
    fun importStrategy(showImporters: List<ShowImporter>) = DefaultImporterSelectionStrategy(showImporters)

    @Bean
    fun contentRefreshDaemon(contentRefreshService: ContentRefreshService) = ContentRefreshDaemon(contentRefreshService)

    @Bean(name = arrayOf("threadPoolTaskExecutor"))
    fun threadPoolTaskExecutor(): Executor = ThreadPoolTaskExecutor()

    @Bean
    fun taskExecutor(): Executor = ThreadPoolTaskExecutor()
        .apply {
            corePoolSize = 2
            maxPoolSize = 10
        }

    @Bean(name = arrayOf(contentRefreshTaskExecutorBeanName))
    fun contentRefreshTaskExecutor(): Executor = ThreadPoolTaskExecutor()
        .apply {
            corePoolSize = 1
            maxPoolSize = 1
        }

    @Bean(name = [applicationEventMulticaster])
    fun simpleApplicationEventMulticaster(): ApplicationEventMulticaster =
        SimpleApplicationEventMulticaster().apply { setTaskExecutor(SimpleAsyncTaskExecutor()) }

    @Bean
    fun eventRouter() = EventRouter()

    //If the bean is defined this way, it can't be used for @RestClientTest.
//    @Bean
//    fun traktMediaProvider(traktRestTemplate: RestTemplate) = DefaultTraktMediaProvider(traktRestTemplate)
}

const val contentRefreshTaskExecutorBeanName = "contentRefreshTaskExecutor"
const val applicationEventMulticaster = "applicationEventMulticaster"
