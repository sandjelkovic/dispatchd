package com.sandjelkovic.dispatchd.content.data.repository

import com.sandjelkovic.dispatchd.content.data.entity.Season
import com.sandjelkovic.dispatchd.content.data.entity.Show
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
class SeasonRepositoryTest {
    @Autowired
    lateinit var repository: SeasonRepository
    @Autowired
    lateinit var showRepository: ShowRepository

    @Test
    fun saveSeason() {
        val season = Season(description = "Season Description",
                traktId = "traktId",
                number = "4",
                airDate = ZonedDateTime.now().minusDays(5))

        val saved = repository.save(season.copy())

        assertThat(saved)
                .isEqualToIgnoringGivenFields(season, "id", "episodes")
        assertThat(saved.id)
                .isNotNull()
                .isGreaterThan(0)

        val foundOptional = repository.findById(saved.id!!)
        assertThat(foundOptional)
                .isPresent
        assertThat(foundOptional.get())
                .isEqualToIgnoringGivenFields(season, "id", "episodes")
        assertThat(foundOptional.get().id)
                .isNotNull()
                .isGreaterThan(0)
    }

    @Test
    fun saveEmptySeason() {
        val season = Season()

        val saved = repository.save(season.copy())

        assertThat(saved)
                .isEqualToIgnoringGivenFields(season, "id", "episodes")
        assertThat(saved.id)
                .isNotNull()
                .isGreaterThan(0)

        val foundOptional = repository.findById(saved.id!!)
        assertThat(foundOptional)
                .isPresent
        assertThat(foundOptional.get())
                .isEqualToIgnoringGivenFields(season, "id", "episodes")
        assertThat(foundOptional.get().id)
                .isNotNull()
                .isGreaterThan(0)
    }

    @Test
    fun saveSeasonWithNewShow() {
        val season = Season()

        val saved = repository.save(season.copy())

        assertThat(saved)
                .isEqualToIgnoringGivenFields(season, "id", "episodes")
        assertThat(saved.id)
                .isNotNull()
                .isGreaterThan(0)

        val foundOptional = repository.findById(saved.id!!)
        assertThat(foundOptional)
                .isPresent
        assertThat(foundOptional.get())
                .isEqualToIgnoringGivenFields(season, "id", "episodes")
        assertThat(foundOptional.get().id)
                .isNotNull()
                .isGreaterThan(0)
    }

    @Test
    fun findByShow() {
        val show = showRepository.save(Show())
        val season = repository.save(Season(show = show))

        val seasonsByShow = repository.findByShow(show)

        assertThat(seasonsByShow)
                .isNotNull
                .isNotEmpty
                .hasSize(1)
                .first()
                .isEqualTo(season)
    }


    @Test
    fun findByShowMultipleEpisodes() {
        val show = showRepository.save(Show())
        val seasons = repository.saveAll(listOf(
                Season(show = show),
                Season(show = show),
                Season(show = show)
        ))

        val seasonsByShow = repository.findByShow(show)

        assertThat(seasonsByShow)
                .isNotNull
                .isNotEmpty
                .hasSize(3)
                .doesNotHaveDuplicates()
                .containsAll(seasons)
    }

    @Test
    fun findByShowPageable() {
        val show = showRepository.save(Show())
        val seasons = repository.saveAll(listOf(
                Season(show = show),
                Season(show = show),
                Season(show = show),
                Season(show = show),
                Season(show = show)
        ))
        val pageable = PageRequest.of(1, 2) // second Page, pages start from 0

        val page = repository.findByShow(show, pageable)

        assertThat(page)
                .isNotNull
                .isNotEmpty
                .hasSize(2)
                .doesNotHaveDuplicates()
        assertThat(page.totalElements).isEqualTo(seasons.count().toLong())
        assertThat(page.totalPages).isEqualTo(3)
    }


}
