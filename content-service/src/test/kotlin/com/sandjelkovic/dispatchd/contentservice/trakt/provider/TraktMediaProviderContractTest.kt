package com.sandjelkovic.dispatchd.contentservice.trakt.provider

import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
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
            isPresent {
                assertk.assert(it.actual.title, "Show Title").isNotNull {
                    it.isEqualTo("Star Trek: The Next Generation")
                }
                assertk.assert(it.actual.year, "Show Year").isNotNull {
                    it.isEqualTo(1987)
                }
            }
        }
    }
}
