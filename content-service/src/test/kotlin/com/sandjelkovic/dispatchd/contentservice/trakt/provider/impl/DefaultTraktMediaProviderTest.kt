package com.sandjelkovic.dispatchd.contentservice.trakt.provider.impl

import arrow.core.Either
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.sandjelkovic.dispatchd.contentservice.service.RemoteServiceException
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowUpdateTrakt
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.net.URI
import java.time.LocalDate
import java.time.ZonedDateTime


/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
@RunWith(SpringRunner::class)
class DefaultTraktMediaProviderTest {
    companion object {
        const val showId = "star-trek-tng"

    }

    private val uri: URI = URI.create("https://baseUrl.com/")
    private val showTrakt: ShowTrakt = ShowTrakt(title = "Title")

    private val mockRestTemplate: RestTemplate = mock {}

    private val mockUriProvider: DefaultTraktUriProvider = mock {
        on(it.getShowUri(showId)) doReturn uri
        on(it.getUpdatesUri(LocalDate.now().minusDays(1))) doReturn uri
    }
    private lateinit var provider: DefaultTraktMediaProvider

    @Before
    fun setUp() {
        provider = DefaultTraktMediaProvider(mockUriProvider, mockRestTemplate)
    }

    @Test
    fun `getShow should return retrieved show`() {
        val preparedShow = showTrakt
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

    @Test
    fun `getShow should throw a RemoteServiceException because there was an HTTP Exception`() {
        `when`(mockRestTemplate.getForObject<ShowTrakt>(uri))
                .thenThrow(HttpClientErrorException::class.java)

        val throwable = catchThrowable { provider.getShow(showId) }

        assertThat(throwable).isInstanceOf(RemoteServiceException::class.java)

        verify(mockRestTemplate).getForObject<ShowTrakt>(uri)
        verify(mockUriProvider).getShowUri(showId)
    }

    @Test
    fun `Should retrieve updates`() {
        val fromDate = LocalDate.now().minusDays(1)
        val oneUpdate = ShowUpdateTrakt(ZonedDateTime.now().minusHours(10))
        `when`(mockRestTemplate.getForObject<Array<ShowUpdateTrakt>>(uri))
                .thenReturn(arrayOf(oneUpdate))

        val updates = provider.getUpdates(fromDate)

        assert(updates.isRight())
        assertThat((updates as Either.Right).b).isNotEmpty.hasSize(1).contains(oneUpdate)

        verify(mockRestTemplate).getForObject<Array<ShowUpdateTrakt>>(uri)
        verify(mockUriProvider).getUpdatesUri(fromDate)
    }

}
