package com.sandjelkovic.dispatchd.contentservice.trakt.provider

import com.fasterxml.jackson.databind.ObjectMapper
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.impl.DefaultTraktMediaProvider
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess


/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
@RunWith(SpringRunner::class)
@RestClientTest(DefaultTraktMediaProvider::class)
@AutoConfigureWebClient(registerRestTemplate = true)
@ImportAutoConfiguration(RefreshAutoConfiguration::class)
class TraktMediaProviderIntegrationTest {

    @Autowired
    lateinit var traktMediaProvider: DefaultTraktMediaProvider
    @Autowired
    lateinit var server: MockRestServiceServer
    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun getTvShow() {
        val showId = "star-trek-tng"
        server.expect(requestTo("https://api.trakt.tv/shows/$showId?extended=full,images"))
                .andRespond(withSuccess()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(Show())))
        val show = traktMediaProvider.getShow(showId)

    }
}
