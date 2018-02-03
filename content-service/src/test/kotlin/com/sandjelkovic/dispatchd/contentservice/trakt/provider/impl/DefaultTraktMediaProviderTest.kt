package com.sandjelkovic.dispatchd.contentservice.trakt.provider.impl

import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowTrakt
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.net.URI


/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
@RunWith(SpringRunner::class)
class DefaultTraktMediaProviderTest {

    @Test
    fun getTvShow() {
        val mockRestTemplate = mock(RestTemplate::class.java)
        val provider = DefaultTraktMediaProvider(mockRestTemplate)
        provider.baseServiceUrl = "http://something.net/api"

        val showId = "star-trek-tng"
        val uri = URI.create("${provider.baseServiceUrl}/shows/$showId?extended=full,images")

        val preparedShow = ShowTrakt()
        `when`(mockRestTemplate.getForObject<ShowTrakt>(uri))
                .thenReturn(preparedShow)

        val tvShow = provider.getShow(showId)

        assertThat(tvShow)
                .isEqualTo(preparedShow)
        verify(mockRestTemplate).getForObject<ShowTrakt>(uri)
    }
}
