package com.sandjelkovic.dispatchd.content.data.repository

import com.sandjelkovic.dispatchd.content.data.entity.UpdateJob
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import java.time.ZonedDateTime
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * @author sandjelkovic
 * @date 21.4.18.
 */
@RunWith(SpringRunner::class)
@DataJpaTest
class ImportJobRepositoryTest {
    @Autowired
    lateinit var repository: UpdateJobRepository

    @Before
    fun setUp() {
        repository.deleteAll()
    }

    @Test
    fun `Should save and return entity with ID`() {
        val job = repository.save(UpdateJob())

        assert(repository.count() == 1L)
        assertNotNull(job)
        assertNotNull(job.id)
    }

    @Test
    fun `Should return correct job based on start or finishing datetime`() {
        val secondJob = repository.save(UpdateJob(startTime = ZonedDateTime.now().minusMinutes(1), finishTime = ZonedDateTime.now().minusSeconds(10)))
        repository.save(UpdateJob(startTime = ZonedDateTime.now().minusMinutes(5), finishTime = ZonedDateTime.now().minusMinutes(4)))

        val lastEntryByStartTime = repository.findFirstByOrderByStartTimeDesc()
        assertTrue { lastEntryByStartTime.isPresent }
        assertTrue { lastEntryByStartTime.get() == secondJob }

        val lastEntryByFinishingTime = repository.findFirstByOrderByFinishTimeDesc()
        assertTrue { lastEntryByFinishingTime.isPresent }
        assertTrue { lastEntryByFinishingTime.get() == secondJob }
    }

}
