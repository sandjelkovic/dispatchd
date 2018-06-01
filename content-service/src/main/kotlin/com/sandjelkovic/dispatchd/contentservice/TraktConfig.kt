package com.sandjelkovic.dispatchd.contentservice

import com.sandjelkovic.dispatchd.contentservice.data.repository.EpisodeRepository
import com.sandjelkovic.dispatchd.contentservice.data.repository.SeasonRepository
import com.sandjelkovic.dispatchd.contentservice.data.repository.ShowRepository
import com.sandjelkovic.dispatchd.contentservice.trakt.converter.Trakt2EpisodeConverter
import com.sandjelkovic.dispatchd.contentservice.trakt.converter.Trakt2SeasonConverter
import com.sandjelkovic.dispatchd.contentservice.trakt.converter.Trakt2ShowConverter
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktMediaProvider
import com.sandjelkovic.dispatchd.contentservice.trakt.service.TraktShowImporter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.ConversionService
import org.springframework.scheduling.annotation.EnableAsync

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
        var appSecret: String) {

    @Bean
    fun traktImporter(showRepository: ShowRepository, seasonRepository: SeasonRepository,
                      episodeRepository: EpisodeRepository, @Qualifier("mvcConversionService") conversionService: ConversionService, provider: TraktMediaProvider) =
            TraktShowImporter(showRepository, seasonRepository, episodeRepository, conversionService, provider)

    @Bean
    fun trakt2EpisodeConverter() = Trakt2EpisodeConverter()

    @Bean
    fun trakt2SeasonConverter() = Trakt2SeasonConverter()

    @Bean
    fun trakt2ShowConverter() = Trakt2ShowConverter()

}
