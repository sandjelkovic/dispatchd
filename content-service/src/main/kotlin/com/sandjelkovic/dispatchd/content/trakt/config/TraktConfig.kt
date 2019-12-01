package com.sandjelkovic.dispatchd.content.trakt.config

import com.sandjelkovic.dispatchd.content.data.repository.EpisodeRepository
import com.sandjelkovic.dispatchd.content.data.repository.SeasonRepository
import com.sandjelkovic.dispatchd.content.data.repository.ShowRepository
import com.sandjelkovic.dispatchd.content.trakt.converter.Trakt2EpisodeConverter
import com.sandjelkovic.dispatchd.content.trakt.converter.Trakt2SeasonConverter
import com.sandjelkovic.dispatchd.content.trakt.converter.Trakt2ShowConverter
import com.sandjelkovic.dispatchd.content.trakt.interceptor.HeaderRequestInterceptor
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktMediaProvider
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktUriProvider
import com.sandjelkovic.dispatchd.content.trakt.provider.impl.DefaultTraktMediaProvider
import com.sandjelkovic.dispatchd.content.trakt.provider.impl.DefaultTraktUriProvider
import com.sandjelkovic.dispatchd.content.trakt.service.TraktShowImporter
import mu.KLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.ConversionService
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.client.RestTemplate

/**
 * @author sandjelkovic
 * @date 5.5.18.
 */
@Configuration
@RefreshScope
@EnableAsync
class TraktConfig(
    val traktProperties: TraktProperties
) {
    companion object : KLogging()

    @Bean
    fun traktImporter(
        showRepository: ShowRepository, seasonRepository: SeasonRepository,
        episodeRepository: EpisodeRepository, @Qualifier("mvcConversionService") conversionService: ConversionService,
        provider: TraktMediaProvider,
        applicationEventPublisher: ApplicationEventPublisher
    ) =
        TraktShowImporter(showRepository, seasonRepository, episodeRepository, conversionService, provider, applicationEventPublisher)

    @Bean
    @RefreshScope
    fun traktUriProvider() = DefaultTraktUriProvider(traktProperties)

    @Bean
    @RefreshScope
    fun traktMediaProvider(traktRestTemplate: RestTemplate, traktUriProvider: TraktUriProvider) = DefaultTraktMediaProvider(traktUriProvider, traktRestTemplate)

    @Bean
    @RefreshScope
    fun traktRestTemplate(): RestTemplate = RestTemplate().apply {
        logger.info { "Configuring TraktRestTemplate" }
        logger.info { "Trakt base URL is ${traktProperties.baseServiceUrl}" }
        interceptors.addAll(
            listOf(
                HeaderRequestInterceptor("Content-type", "application/json"),
                HeaderRequestInterceptor("trakt-api-key", traktProperties.appId),
                HeaderRequestInterceptor("trakt-api-version", traktProperties.apiVersion)
            )
        )
    }

    @Bean
    fun trakt2EpisodeConverter() = Trakt2EpisodeConverter()

    @Bean
    fun trakt2SeasonConverter() = Trakt2SeasonConverter()

    @Bean
    fun trakt2ShowConverter() = Trakt2ShowConverter()

}
