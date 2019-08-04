package com.sandjelkovic.dispatchd.content.trakt.service

import arrow.core.Either
import com.sandjelkovic.dispatchd.content.data.entity.Episode
import com.sandjelkovic.dispatchd.content.data.entity.Season
import com.sandjelkovic.dispatchd.content.data.entity.Show
import com.sandjelkovic.dispatchd.content.data.repository.EpisodeRepository
import com.sandjelkovic.dispatchd.content.data.repository.SeasonRepository
import com.sandjelkovic.dispatchd.content.data.repository.ShowRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import strikt.api.expectThat
import strikt.assertions.all
import strikt.assertions.isEqualTo

/**
 * @author sandjelkovic
 * @date 2019-08-04
 */
@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
class TraktShowImporterIntegrationTest {

    @Autowired
    lateinit var showImporter: TraktShowImporter

    @Autowired
    lateinit var showRepository: ShowRepository

    @Autowired
    lateinit var seasonRepository: SeasonRepository

    @Autowired
    lateinit var episodeRepository: EpisodeRepository

    @Before
    fun setUp() {
        episodeRepository.deleteAll()
        seasonRepository.deleteAll()
        showRepository.deleteAll()
    }

//    @After
//    fun after() {
//        episodeRepository.deleteAll()
//        seasonRepository.deleteAll()
//        showRepository.deleteAll()
//    }

    @Test
    fun `should save empty Show`() {
        showImporter.saveImportedShow(Show())

        expectThat(showRepository.count()).isEqualTo(1)
    }

    @Test
    fun `should save a new biconnected Show`() {
        val testShow = biconnectedShow()

        val either = showImporter.saveImportedShow(testShow)

        expectThat(showRepository.count()).isEqualTo(1)
        expectThat(seasonRepository.count()).isEqualTo(1)
        expectThat(episodeRepository.count()).isEqualTo(2)
        expectThat(episodeRepository.findAll()).all {
            val right = either as Either.Right
            get { show?.id }.isEqualTo(right.b.id)
            get { season?.number }.isEqualTo("1")
        }
    }

    @Test
    fun `should save a new unconnected Show`() {
        val season1 = Season(number = "1")
        val testSeasons = listOf(season1)
        val season1episodes = listOf(Episode(title = "Test episode", seasonNumber = "1"), Episode(title = "Test episode Two", seasonNumber = "1"))
        val testShow = Show(title = "Test unconnected Show", seasons = testSeasons, episodes = season1episodes)

        val either = showImporter.saveImportedShow(testShow)

        expectThat(showRepository.count()).isEqualTo(1)
        expectThat(seasonRepository.count()).isEqualTo(1)
        expectThat(episodeRepository.count()).isEqualTo(2)

        expectThat(episodeRepository.findAll()).all {
            val right = either as Either.Right
            get { show?.id }.isEqualTo(right.b.id)
            get { season?.number }.isEqualTo("1")
        }
    }

    @Test
    fun `should save a new Show one way connected`() {
        val season1 = Season(number = "1")
        val testSeasons = listOf(season1)
        val season1episodes = listOf(Episode(title = "Test episode", seasonNumber = "1"), Episode(title = "Test episode Two", seasonNumber = "1"))
        val testShow = Show(title = "Test one way connected Show", seasons = testSeasons, episodes = season1episodes)
        season1episodes.forEach { it.season = season1 }

        val either = showImporter.saveImportedShow(testShow)

        expectThat(showRepository.count()).isEqualTo(1)
        expectThat(seasonRepository.count()).isEqualTo(1)
        expectThat(episodeRepository.count()).isEqualTo(2)

        expectThat(episodeRepository.findAll()).all {
            val right = either as Either.Right
            get { show?.id }.isEqualTo(right.b.id)
            get { season?.number }.isEqualTo("1")
        }
    }

    @Test
    fun `should save an updated Show - unconnected`() {
        val biconnectedShow = showRepository.save(biconnectedShow())
        seasonRepository.saveAll(biconnectedShow.seasons)
        episodeRepository.saveAll(biconnectedShow.episodes)


        val season1 = Season(number = "1")
        val testSeasons = listOf(season1)
        val season1episodes = listOf(Episode(title = "Test episode", seasonNumber = "1"), Episode(title = "Test episode Two", seasonNumber = "1"))
        val testShow = Show(title = "Test unconnected Show", seasons = testSeasons, episodes = season1episodes, id = biconnectedShow.id, traktId = "test trakt id")

        val either = showImporter.saveImportedShow(testShow)

        expectThat(showRepository.count()).isEqualTo(1)
        expectThat(seasonRepository.count()).isEqualTo(1)
        expectThat(episodeRepository.count()).isEqualTo(2)

        expectThat(episodeRepository.findAll()).all {
            val right = either as Either.Right
            get { show?.id }.isEqualTo(right.b.id)
            get { season?.number }.isEqualTo("1")
        }
    }

    private fun biconnectedShow(): Show {
        val season1 = Season(number = "1")
        val testSeasons = listOf(season1)
        val season1episodes = listOf(Episode(title = "Test episode", seasonNumber = "1"), Episode(title = "Test episode Two", seasonNumber = "1"))
        val testShow = Show(title = "Test biconnectedShow", seasons = testSeasons, episodes = season1episodes, traktId = "test trakt id")
        testSeasons.forEach {
            it.episodes = season1episodes
            it.show = testShow
        }
        season1episodes.forEach {
            it.show = testShow
            it.season = season1
        }
        return testShow
    }
}
