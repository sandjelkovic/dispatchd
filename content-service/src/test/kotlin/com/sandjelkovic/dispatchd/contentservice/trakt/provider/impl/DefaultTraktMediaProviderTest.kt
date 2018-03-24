package com.sandjelkovic.dispatchd.contentservice.trakt.provider.impl

import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowTrakt
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
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
    companion object {
        const val showId = "star-trek-tng"

    }

    private lateinit var mockRestTemplate: RestTemplate
    private lateinit var mockUriProvider: DefaultTraktUriProvider
    private lateinit var uri: URI
    private lateinit var provider: DefaultTraktMediaProvider

    @Before
    fun setUp() {
        mockRestTemplate = mock(RestTemplate::class.java)
        mockUriProvider = mock(DefaultTraktUriProvider::class.java)
        uri = URI.create("https://baseUrl.com/shows/$showId?extended=full,images")
        `when`(mockUriProvider.getShowUri(showId))
                .thenReturn(uri)

        provider = DefaultTraktMediaProvider(mockUriProvider, mockRestTemplate)
    }

    @Test
    fun `getShow should return retrieved show`() {
        val preparedShow = ShowTrakt(title = "Title")
        `when`(mockRestTemplate.getForObject<ShowTrakt>(uri))
                .thenReturn(preparedShow)

        val showOptional = provider.getShow(showId)

        assertThat(showOptional)
                .isPresent
                .hasValue(preparedShow)
        verify(mockRestTemplate).getForObject<ShowTrakt>(uri)
        verify(mockUriProvider).getShowUri(showId)
    }

    @Test
    fun `getShow should return empty Optional because the Show couldn't be retrieved`() {
        `when`(mockRestTemplate.getForObject<ShowTrakt>(uri))
                .thenReturn(null)

        val showOptional = provider.getShow(showId)

        assertThat(showOptional).isNotPresent

        verify(mockRestTemplate).getForObject<ShowTrakt>(uri)
        verify(mockUriProvider).getShowUri(showId)
    }
}
