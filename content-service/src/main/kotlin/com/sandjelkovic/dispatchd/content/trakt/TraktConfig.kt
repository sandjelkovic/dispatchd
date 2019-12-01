package com.sandjelkovic.dispatchd.content.trakt

import com.sandjelkovic.dispatchd.content.data.repository.EpisodeRepository
import com.sandjelkovic.dispatchd.content.data.repository.SeasonRepository
import com.sandjelkovic.dispatchd.content.data.repository.ShowRepository
import com.sandjelkovic.dispatchd.content.trakt.converter.Trakt2EpisodeConverter
import com.sandjelkovic.dispatchd.content.trakt.converter.Trakt2SeasonConverter
import com.sandjelkovic.dispatchd.content.trakt.converter.Trakt2ShowConverter
import com.sandjelkovic.dispatchd.content.trakt.interceptor.HeaderRequestInterceptor
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktMediaProvider
import com.sandjelkovic.dispatchd.content.trakt.provider.impl.DefaultTraktMediaProvider
import com.sandjelkovic.dispatchd.content.trakt.provider.impl.DefaultTraktUriProvider
import com.sandjelkovic.dispatchd.content.trakt.service.TraktShowImporter
import mu.KLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
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
    @Value("\${trakt.baseServiceUrl: }")
    var baseServiceUrl: String,
    @Value("\${trakt.appSecret: }")
    var appSecret: String
) {
    companion object : KLogging()

    @Bean
    fun traktImporter(
        showRepository: ShowRepository, seasonRepository: SeasonRepository,
        episodeRepository: EpisodeRepository, @Qualifier("mvcConversionService") conversionService: ConversionService, provider: TraktMediaProvider,
        applicationEventPublisher: ApplicationEventPublisher
    ) = TraktShowImporter(showRepository, seasonRepository, episodeRepository, conversionService, provider, applicationEventPublisher)

    @Bean
    @RefreshScope
    fun traktUriProvider() = DefaultTraktUriProvider()

    @Bean
    @RefreshScope
    fun traktMediaProvider(traktRestTemplate: RestTemplate) = DefaultTraktMediaProvider(traktUriProvider(), traktRestTemplate)

    @Bean
    @RefreshScope
    fun traktRestTemplate(
        @Value("\${trakt.appId: }") appId: String,
        @Value("\${trakt.apiVersion: }") apiVersion: String
    ): RestTemplate = RestTemplate().apply {
        interceptors.addAll(
            listOf(
                HeaderRequestInterceptor("Content-type", "application/json"),
                HeaderRequestInterceptor("trakt-api-key", appId),
                HeaderRequestInterceptor("trakt-api-version", apiVersion)
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
