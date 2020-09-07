package com.sandjelkovic.dispatchd.content.service.impl

import arrow.core.Either
import assertk.assert
import assertk.assertions.isEqualTo
import com.sandjelkovic.dispatchd.content.data.entity.ImportStatus
import com.sandjelkovic.dispatchd.content.data.repository.ImportStatusRepository
import com.sandjelkovic.dispatchd.content.service.ImportService
import com.sandjelkovic.dispatchd.content.service.ImporterSelectionStrategy
import com.sandjelkovic.dispatchd.content.service.InvalidImportUrlException
import com.sandjelkovic.dispatchd.content.service.UnsupportedBackendException
import com.sandjelkovic.dispatchd.isEmpty
import com.sandjelkovic.dispatchd.isNotEmpty
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.net.URI
import java.util.*

/**
 * @author sandjelkovic
 * @date 21.4.18.
 */

class ImportServiceTest {

    private val kotlinLangURI = URI.create("https://kotlinlang.org/")
    private val traktShowURI = URI.create("https://trakt.tv/shows/the-expanse")
    private val validStatusId = 5L
    private val importStatus: ImportStatus = ImportStatus()
    private val mockRepository: ImportStatusRepository = mockk {
        every { save(importStatus) } returns importStatus.copy(id = 100L)
        every { findById(validStatusId) } returns Optional.of(importStatus.copy(id = validStatusId))
        every { findById(match { it != validStatusId }) } returns Optional.empty<ImportStatus>()
        every { save(any()) } returns ImportStatus()
    }
    private val mockImporterSelectionStrategy: ImporterSelectionStrategy = mockk {
        every { getImporter(kotlinLangURI) } returns Either.left(UnsupportedBackendException())
    }

    private val taskExecutor: TaskExecutor = ThreadPoolTaskExecutor()
        .apply {
            corePoolSize = 1
            maxPoolSize = 2
        }

    private lateinit var service: ImportService

    @Before
    fun setUp() {
        service = ImportService(mockRepository, mockImporterSelectionStrategy, taskExecutor)
    }

    @Test
    fun `should return one status`() {
        assert { service.getImportStatus(validStatusId) }.returnedValue {
            isNotEmpty {
                it.isEqualTo(importStatus.copy(id = validStatusId))
            }
        }

        verify { mockRepository.findById(validStatusId) }
    }

    @Test
    fun `should return empty optional`() {
        val id = 5555L

        assert { service.getImportStatus(id) }.returnedValue {
            isEmpty()
        }

        verify { mockRepository.findById(id) }
    }

    @Test
    fun `should return invalid url`() {

        val either = service.importFromUri(kotlinLangURI)

        assert(either.isLeft())
        either as Either.Left
        assert(either.a is InvalidImportUrlException)
    }
}
