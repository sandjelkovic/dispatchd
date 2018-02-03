package com.sandjelkovic.dispatchd.contentservice.trakt.provider

import com.fasterxml.jackson.databind.ObjectMapper
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.impl.DefaultTraktMediaProvider
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.containsString
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
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

    private val resourceResolver = PathMatchingResourcePatternResolver()

    @Before
    fun setUp() {
        val stubsRoot = "/stubs"
        loadSubResourcesRecursive("", stubsRoot)
    }

    private fun loadSubResourcesRecursive(resourcePath: String, root: String) {
        resourceResolver
                .getResources("${root}${resourcePath}/*")
                .map { it.filename }
                .forEach { resourceName ->
                    val subResourcePath = "${resourcePath}/$resourceName"
                    loadThis(subResourcePath, root)
                    loadSubResourcesRecursive(subResourcePath, root)
                }
    }

    private fun loadThis(resourceURL: String, root: String) {
        resourceResolver.getResources("${root}/${resourceURL}/this.*")
                .forEach {
                    val mediaType = convertFileTypeToMediaType(it.filename!!)
                    server.expect(requestTo(containsString(resourceURL))) // remove /stubs at start
                            .andRespond(withSuccess()
                                    .contentType(mediaType)
                                    .body(it))
                }
    }

    private fun convertFileTypeToMediaType(filename: String) =
            if (filename.endsWith(".json")) MediaType.APPLICATION_JSON
            else MediaType.ALL

    @Test
    @Ignore
    fun getTvShow() {
        val showId = "star-trek-the-next-generation"
//        val classPathResource = ClassPathResource("/stubs/shows/star-trek-the-next-generation/this.json")
//        server.expect(requestTo("https://api.trakt.tv/shows/$showId?extended=full,images"))
//                .andRespond(withSuccess()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(classPathResource))
        val show = traktMediaProvider.getShow(showId)
        assertThat(show.title)
                .isEqualTo("Star Trek: The Next Generation")
    }
}
