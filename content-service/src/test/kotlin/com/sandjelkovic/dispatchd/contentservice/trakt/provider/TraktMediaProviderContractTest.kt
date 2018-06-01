package com.sandjelkovic.dispatchd.contentservice.trakt.provider

import assertk.assertions.*
import com.sandjelkovic.dispatchd.isEmpty
import com.sandjelkovic.dispatchd.isPresent
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner


/**
 * @author sandjelkovic
 * @date 4.5.18.
 */
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = ["trakt.baseServiceUrl=http://localhost:${TraktMediaProviderContractTest.wireMockPort}"])
@AutoConfigureWireMock(port = TraktMediaProviderContractTest.wireMockPort, stubs = ["classpath:/stubs/shows"])
@DirtiesContext
class TraktMediaProviderContractTest {
    companion object {
        const val wireMockPort = 11000
    }

    @Autowired
    private lateinit var provider: TraktMediaProvider

    @Test
    fun `should get and deserialize a show`() {
        assertk.assert { provider.getShow("star-trek-the-next-generation") }.returnedValue {
            isPresent() {
                assertk.assert(it.actual.title, "Show Title").isNotNull {
                    it.isEqualTo("Star Trek: The Next Generation")
                }
                assertk.assert(it.actual.year, "Show Year").isNotNull {
                    it.isEqualTo(1987)
                }
            }
        }
    }

    @Test
    fun `should not throw an exception if show can't be found, but return an empty optional`() {
        assertk.assert { provider.getShow("star-trek-the-next-generation-random") }.returnedValue {
            isEmpty()
        }
    }

    @Test
    fun `should get and deserialize seasons of a show`() {
        assertk.assert { provider.getSeasons("star-trek-the-next-generation") }.returnedValue {
            hasSize(8)
            each {
                with(it.actual) {
                    assert(number).isNotNull()
                    assert(episodeCount).isNotNull { it.isGreaterThan(0) }
                }
            }
        }
    }
}
