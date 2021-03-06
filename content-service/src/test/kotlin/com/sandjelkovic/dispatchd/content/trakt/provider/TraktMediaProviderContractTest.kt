package com.sandjelkovic.dispatchd.content.trakt.provider

import com.sandjelkovic.dispatchd.content.isNone
import com.sandjelkovic.dispatchd.content.isSome
import com.sandjelkovic.dispatchd.content.isSuccess
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import strikt.api.expectThat
import strikt.assertions.*


/**
 * @author sandjelkovic
 * @date 4.5.18.
 */
@RunWith(SpringRunner::class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    properties = ["trakt.baseServiceUrl=http://localhost:${TraktMediaProviderContractTest.wireMockPort}"]
)
@AutoConfigureWireMock(port = TraktMediaProviderContractTest.wireMockPort, stubs = ["classpath:/stubs/shows"])
@DirtiesContext
class TraktMediaProviderContractTest {
    companion object {
        const val wireMockPort = 11000
    }

    private val correctShowId = "star-trek-the-next-generation"
    private val notExistingShowId = "star-trek-the-next-generation-random"

    @Autowired
    private lateinit var provider: TraktMediaProvider

    @Test
    fun `Should get and deserialize a show`() {
        expectThat(provider.getShow(correctShowId))
            .isSuccess {
                isSome {
                    get { title }.isNotNull().isEqualTo("Star Trek: The Next Generation")
                    get { year }.isNotNull().isEqualTo(1987)
                }
            }
    }

    @Test
    fun `Should not throw an exception if show can't be found, but return an empty option`() {
        expectThat(provider.getShow(notExistingShowId))
            .isSuccess()
            .isNone()
    }

    @Test
    fun `Should get and deserialize seasons of a show`() {
        expectThat(provider.getSeasons(correctShowId))
            .isSuccess {
                hasSize(8)
                all {
                    get { number }.isNotNull()
                    get { episodeCount }.isNotNull().isGreaterThan(0)
                }
            }
    }

    @Test
    fun `Should get and deserialize minimal information seasons of a show`() {
        expectThat(provider.getSeasonsMinimal(correctShowId))
            .isSuccess {
                hasSize(8)
                all {
                    get { number }.isNotNull().isNotEmpty()
                    get { ids["trakt"] }.isNotNull().isNotEmpty()
                }
            }
    }

    @Test
    fun `Should get and deserialize episodes of a show`() {
        val numberOfSeasons = 7
        val episodesPerSeason = 26
        val seasonZeroNumberOfEpisodes = 1

        expectThat(provider.getShowEpisodes(correctShowId))
            .isSuccess {
                hasSize(numberOfSeasons * episodesPerSeason + seasonZeroNumberOfEpisodes)
                all {
                    get { number }.isNotNull()
                    get { title }.isNotNull()
                }
            }
    }
}
