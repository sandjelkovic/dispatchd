package com.sandjelkovic.dispatchd.content.trakt.provider.impl

import arrow.core.Option
import arrow.core.Try
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.sandjelkovic.dispatchd.content.isFailure
import com.sandjelkovic.dispatchd.content.isNone
import com.sandjelkovic.dispatchd.content.isSome
import com.sandjelkovic.dispatchd.content.isSuccess
import com.sandjelkovic.dispatchd.content.trakt.dto.ShowTrakt
import com.sandjelkovic.dispatchd.content.trakt.dto.ShowUpdateTrakt
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import strikt.api.expectThat
import strikt.assertions.*
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
    fun `getShow should return wrapped retrieved show`() {
        val preparedShow = showTrakt
        `when`(mockRestTemplate.getForObject<ShowTrakt>(uri))
            .thenReturn(preparedShow)

        val attemptedShowOptional: Try<Option<ShowTrakt>> = provider.getShow(showId)

        expectThat(attemptedShowOptional)
            .isSuccess {
                isSome {
                    isEqualTo(preparedShow)
                }
            }

        verify(mockRestTemplate).getForObject<ShowTrakt>(uri)
        verify(mockUriProvider).getShowUri(showId)
    }

    @Test
    fun `getShow should return wrapped empty Option in case the Show is null`() {
        `when`(mockRestTemplate.getForObject<ShowTrakt>(uri))
            .thenReturn(null)

        val attemptedShowOptional: Try<Option<ShowTrakt>> = provider.getShow(showId)

        expectThat(attemptedShowOptional)
            .isSuccess {
                isNone()
            }

        verify(mockRestTemplate).getForObject<ShowTrakt>(uri)
        verify(mockUriProvider).getShowUri(showId)
    }

    @Test
    fun `getShow should return wrapped empty Option in case of a Not Found response`() {
        `when`(mockRestTemplate.getForObject<ShowTrakt>(uri))
            .thenThrow(HttpClientErrorException(HttpStatus.NOT_FOUND))

        val attemptedShowOptional: Try<Option<ShowTrakt>> = provider.getShow(showId)

        expectThat(attemptedShowOptional)
            .isSuccess {
                isNone()
            }

        verify(mockRestTemplate).getForObject<ShowTrakt>(uri)
        verify(mockUriProvider).getShowUri(showId)
    }

    @Test
    fun `getShow should return a Failure if there an unknown HTTP Exception is thrown`() {
        `when`(mockRestTemplate.getForObject<ShowTrakt>(uri))
            .thenThrow(HttpClientErrorException::class.java)

        expectThat(provider.getShow(showId))
            .isFailure {
                isA<HttpClientErrorException>()
            }

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

        expectThat(updates)
            .isSuccess {
                isNotEmpty()
                hasSize(1)
                contains(oneUpdate)
            }

        verify(mockRestTemplate).getForObject<Array<ShowUpdateTrakt>>(uri)
        verify(mockUriProvider).getUpdatesUri(fromDate)
    }

}
