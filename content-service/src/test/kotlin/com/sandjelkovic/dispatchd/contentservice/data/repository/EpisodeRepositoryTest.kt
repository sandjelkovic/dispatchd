package com.sandjelkovic.dispatchd.contentservice.data.repository

import com.sandjelkovic.dispatchd.contentservice.data.entity.Episode
import com.sandjelkovic.dispatchd.contentservice.data.entity.Season
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit4.SpringRunner
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 27.1.18.
 */
@RunWith(SpringRunner::class)
@DataJpaTest
class EpisodeRepositoryTest {
    @Autowired
    lateinit var repository: EpisodeRepository
    @Autowired
    lateinit var showRepository: ShowRepository
    @Autowired
    lateinit var seasonRepository: SeasonRepository

    @Test
    fun saveEpisode() {
        val episode = exampleEpisode()

        val saved = repository.save(episode.copy())

        assertThat(saved)
                .isEqualToIgnoringNullFields(episode)
        assertThat(saved.id)
                .isNotNull()
                .isGreaterThan(0)

        val foundOptional = repository.findById(saved.id!!)
        assertThat(foundOptional)
                .isPresent
        assertThat(foundOptional.get())
                .isEqualToIgnoringNullFields(episode)
        assertThat(foundOptional.get().id)
                .isNotNull()
                .isGreaterThan(0)
    }

    @Test
    fun saveEmptyEpisode() {
        val episode = Episode()

        val saved = repository.save(episode.copy())

        assertThat(saved)
                .isEqualToIgnoringNullFields(episode)
        assertThat(saved.id)
                .isNotNull()
                .isGreaterThan(0)

        val foundOptional = repository.findById(saved.id!!)
        assertThat(foundOptional)
                .isPresent
        assertThat(foundOptional.get())
                .isEqualToIgnoringNullFields(episode)
        assertThat(foundOptional.get().id)
                .isNotNull()
                .isGreaterThan(0)
    }

    @Test
    fun saveEpisodeWithNewShow() {
        val episode = Episode(show = Show())

        val saved = repository.save(episode.copy())

        assertThat(saved)
                .isEqualToIgnoringNullFields(episode)
        assertThat(saved.id)
                .isNotNull()
                .isGreaterThan(0)

        val foundOptional = repository.findById(saved.id!!)
        assertThat(foundOptional)
                .isPresent
        assertThat(foundOptional.get())
                .isEqualToIgnoringNullFields(episode)
        assertThat(foundOptional.get().id)
                .isNotNull()
                .isGreaterThan(0)
    }

    @Test
    fun findByShow() {
        val show = showRepository.save(Show())
        val episode = repository.save(Episode(show = show))

        val episodeByShow = repository.findByShow(show)

        assertThat(episodeByShow)
                .isNotNull
                .isNotEmpty
                .hasSize(1)
                .first()
                .isEqualTo(episode)
    }

    @Test
    fun findByShowMultipleEpisodes() {
        val show = showRepository.save(Show())
        val episodes = repository.saveAll(listOf(
                Episode(show = show),
                Episode(show = show),
                Episode(show = show)
        ))

        val episodeByShow = repository.findByShow(show)

        assertThat(episodeByShow)
                .isNotNull
                .isNotEmpty
                .hasSize(3)
                .doesNotHaveDuplicates()
                .containsAll(episodes)
    }

    @Test
    fun findByShowPageable() {
        val show = showRepository.save(Show())
        val episodes = repository.saveAll(listOf(
                Episode(show = show),
                Episode(show = show),
                Episode(show = show),
                Episode(show = show),
                Episode(show = show)
        ))
        val pageable = PageRequest.of(1, 2) // second Page, pages start from 0

        val page = repository.findByShow(show, pageable)

        assertThat(page)
                .isNotNull
                .isNotEmpty
                .hasSize(2)
                .doesNotHaveDuplicates()
        assertThat(page.totalElements).isEqualTo(episodes.count().toLong())
        assertThat(page.totalPages).isEqualTo(3)
    }

    @Test
    fun findBySeason() {
        val season = seasonRepository.save(Season())
        val episode = repository.save(Episode(season = season))

        val episodeBySeason = repository.findBySeason(season)

        assertThat(episodeBySeason)
                .isNotNull
                .isNotEmpty
                .hasSize(1)
                .first()
                .isEqualTo(episode)
    }

    @Test
    fun findBySeasonMultipleEpisodes() {
        val season = seasonRepository.save(Season())
        val episodes = repository.saveAll(listOf(
                Episode(season = season),
                Episode(season = season),
                Episode(season = season)
        ))

        val episodeByShow = repository.findBySeason(season)

        assertThat(episodeByShow)
                .isNotNull
                .isNotEmpty
                .hasSize(3)
                .doesNotHaveDuplicates()
                .containsAll(episodes)
    }

    @Test
    fun findBySeasonPageable() {
        val season = seasonRepository.save(Season())
        val episodes = repository.saveAll(listOf(
                Episode(season = season),
                Episode(season = season),
                Episode(season = season),
                Episode(season = season),
                Episode(season = season)
        ))
        val pageable = PageRequest.of(1, 2) // second Page, pages start from 0

        val page = repository.findBySeason(season, pageable)

        assertThat(page)
                .isNotNull
                .isNotEmpty
                .hasSize(2)
                .doesNotHaveDuplicates()
        assertThat(page.totalElements).isEqualTo(episodes.count().toLong())
        assertThat(page.totalPages).isEqualTo(3)
    }


    private fun exampleEpisode(): Episode {
        return Episode(airDate = ZonedDateTime.now().plusDays(1),
                number = 5,
                traktId = "123456",
                title = "Episode Title",
                description = "Episode Description")
    }
}
