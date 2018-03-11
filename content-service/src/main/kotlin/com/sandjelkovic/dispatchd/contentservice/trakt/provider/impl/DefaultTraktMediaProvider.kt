package com.sandjelkovic.dispatchd.contentservice.trakt.provider.impl

import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktMediaProvider
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktUriProvider
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
@Service
class DefaultTraktMediaProvider(private val traktUriProvider: TraktUriProvider,
                                private val traktRestTemplate: RestTemplate) : TraktMediaProvider {
    companion object : KLogging()

    override fun getShow(showId: String): ShowTrakt {
        val uri = traktUriProvider.getShowUri(showId)

        val show: ShowTrakt? = traktRestTemplate.getForObject(uri)
        return show!!
    }

}
