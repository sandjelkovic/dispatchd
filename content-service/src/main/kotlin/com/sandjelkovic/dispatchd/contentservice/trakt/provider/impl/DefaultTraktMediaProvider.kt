package com.sandjelkovic.dispatchd.contentservice.trakt.provider.impl

import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktMediaProvider
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder

/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
@RefreshScope
@Service
class DefaultTraktMediaProvider(private val traktRestTemplate: RestTemplate) : TraktMediaProvider {
    companion object : KLogging()

    @Value("\${trakt.baseServiceUrl: }")
    lateinit var baseServiceUrl: String

    override fun getShow(showId: String): ShowTrakt {
        val url = UriComponentsBuilder.fromHttpUrl(baseServiceUrl)
                .pathSegment("shows")
                .pathSegment(showId)
                .queryParam("extended", "full,images")
                .build().encode().toUri()

        val show: ShowTrakt? = traktRestTemplate.getForObject(url)
        return show!!
    }
}
