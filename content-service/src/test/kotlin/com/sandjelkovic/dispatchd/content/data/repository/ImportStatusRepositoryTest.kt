package com.sandjelkovic.dispatchd.content.data.repository

import assertk.assert
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import com.sandjelkovic.dispatchd.content.data.entity.ImportStatus
import com.sandjelkovic.dispatchd.isEmpty
import com.sandjelkovic.dispatchd.isInLast
import com.sandjelkovic.dispatchd.isPresent
import com.sandjelkovic.dispatchd.isPresentAndExtracted
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import java.time.Duration
import kotlin.time.seconds
import kotlin.time.toJavaDuration

/**
 * @author sandjelkovic
 * @date 21.4.18.
 */
@RunWith(SpringRunner::class)
@DataJpaTest
@OptIn(kotlin.time.ExperimentalTime::class)
class ImportStatusRepositoryTest {
    @Autowired
    lateinit var repository: ImportStatusRepository

    @Before
    fun setUp() {
        repository.deleteAll()
    }

    @Test
    fun shouldSaveAndReturnEntityWithID() {
        val status = ImportStatus(mediaUrl = mediaUrl)

        val savedStatus = repository.save(status)

        assert(repository.count()).isEqualTo(1L)
        assert(savedStatus).isNotNull()

        with(savedStatus) {
            assert(id).isNotNull()
            assert(finishTime).isNull()
            assert(initiationTime).isInLast(10.seconds.toJavaDuration())
            assert(mediaUrl).isEqualTo(mediaUrl)
        }
    }

    @Test
    fun shouldFindOne() {
        val status = ImportStatus(mediaUrl = mediaUrl)

        val savedStatus = repository.save(status)

        assert { repository.findById(savedStatus.id!!) }.returnedValue {
            isPresentAndExtracted {
                with(it) {
                    assert(id).isNotNull { it.isEqualTo(savedStatus.id!!) }
                    assert(finishTime).isNull()
                    assert(initiationTime).isInLast(Duration.ofSeconds(10))
                    assert(mediaUrl).isEqualTo(mediaUrl)
                }
            }
            // asserting directly on the wrapped variable
            isPresent {
                it.isEqualTo(savedStatus)
            }
        }
    }

    @Test
    fun shouldNotFindAny() {
        val foundEntity = repository.findById(5555L)

        assert(foundEntity).isEmpty()
    }

}

const val mediaUrl = "https://github.com/sandjelkovic/test"
