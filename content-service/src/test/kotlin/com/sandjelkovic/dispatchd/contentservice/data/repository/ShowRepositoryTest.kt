package com.sandjelkovic.dispatchd.contentservice.data.repository

import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.junit4.SpringRunner
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 27.1.18.
 */
@RunWith(SpringRunner::class)
@DataJpaTest
class ShowRepositoryTest {
    @Autowired
    lateinit var repository: ShowRepository

    @Test
    fun saveSeason() {
        val show = exampleShow()

        val saved = repository.save(show.copy())

        assertThat(saved)
            .isEqualToIgnoringGivenFields(show, "id", "episodes", "seasons")
        assertThat(saved.id)
            .isNotNull()
            .isGreaterThan(0)

        val foundOptional = repository.findById(saved.id!!)
        assertThat(foundOptional)
            .isPresent
        assertThat(foundOptional.get())
            .isEqualToIgnoringGivenFields(show, "id", "episodes", "seasons")
        assertThat(foundOptional.get().id)
            .isNotNull()
            .isGreaterThan(0)
    }

    private fun exampleShow(): Show {
        return Show(
            description = "Show Description",
            traktId = "traktId",
            lastLocalUpdate = ZonedDateTime.now().minusYears(1000),
            title = "Title"
        )
    }

    @Test
    fun saveEmptySeason() {
        val show = Show()

        val saved = repository.save(show.copy())

        assertThat(saved)
            .isEqualToIgnoringGivenFields(show, "id", "episodes", "seasons")
        assertThat(saved.id)
            .isNotNull()
            .isGreaterThan(0)

        val foundOptional = repository.findById(saved.id!!)
        assertThat(foundOptional)
            .isPresent
        assertThat(foundOptional.get())
            .isEqualToIgnoringGivenFields(show, "id", "episodes", "seasons")
        assertThat(foundOptional.get().id)
            .isNotNull()
            .isGreaterThan(0)
    }

    @Test
    fun saveSeasonWithNewShow() {
        val show = Show()

        val saved = repository.save(show.copy())

        assertThat(saved)
            .isEqualToIgnoringGivenFields(show, "id", "episodes", "seasons")
        assertThat(saved.id)
            .isNotNull()
            .isGreaterThan(0)

        val foundOptional = repository.findById(saved.id!!)
        assertThat(foundOptional)
            .isPresent
        assertThat(foundOptional.get())
            .isEqualToIgnoringGivenFields(show, "id", "episodes", "seasons")
        assertThat(foundOptional.get().id)
            .isNotNull()
            .isGreaterThan(0)
    }

    @Test
    fun findByTraktId() {
        val show = exampleShow()
        val saved = repository.save(show)

        val foundOptional = repository.findByTraktId(show.traktId!!)
        assertThat(foundOptional)
            .isPresent
        assertThat(foundOptional.get())
            .isEqualTo(saved)
    }

    @Test
    fun findByTitle() {
        val show = exampleShow()
        val saved = repository.save(show)

        val found = repository.findByTitle(show.title)

        assertThat(found)
            .isNotNull
            .isNotEmpty
            .hasSize(1)
            .first()
            .isEqualTo(saved)
    }

    @Test
    fun findByTitleContaining() {
        val show = exampleShow()
        val saved = repository.save(show)

        val found = repository.findByTitleContaining(show.title.substring(2), Pageable.unpaged())

        assertThat(found)
            .isNotNull
            .isNotEmpty
            .hasSize(1)
            .first()
            .isEqualTo(saved)
    }
}

