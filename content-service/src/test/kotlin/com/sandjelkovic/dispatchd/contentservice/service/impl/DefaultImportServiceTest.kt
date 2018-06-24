package com.sandjelkovic.dispatchd.contentservice.service.impl

import assertk.assert
import assertk.assertions.isEqualTo
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportStatus
import com.sandjelkovic.dispatchd.contentservice.data.repository.ImportStatusRepository
import com.sandjelkovic.dispatchd.contentservice.service.ImporterSelectionStrategy
import com.sandjelkovic.dispatchd.isEmpty
import com.sandjelkovic.dispatchd.isPresent
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

/**
 * @author sandjelkovic
 * @date 21.4.18.
 */

@RunWith(SpringRunner::class)
class DefaultImportServiceTest {

    private val validStatusId = 5L
    private val importStatus: ImportStatus = ImportStatus()
    private val mockRepository: ImportStatusRepository = mock {
        on { save(importStatus) } doReturn importStatus.copy(id = 100L)
        on { findById(validStatusId) } doReturn Optional.of(importStatus.copy(id = validStatusId))
        on { findById(ArgumentMatchers.longThat { it != validStatusId }) } doReturn Optional.empty<ImportStatus>()
    }
    private val mockImporterSelectionStrategy: ImporterSelectionStrategy = mock {}
    private val mockSpringAsyncService: SpringAsyncService = mock {}

    private lateinit var service: DefaultImportService

    @Before
    fun setUp() {
        service = DefaultImportService(mockRepository, mockImporterSelectionStrategy, mockSpringAsyncService)
    }

    @Test
    fun `should return one status`() {
        assert { service.getImportStatus(validStatusId) }.returnedValue {
            isPresent {
                it.isEqualTo(importStatus.copy(id = validStatusId))
            }
        }

        verify(mockRepository).findById(validStatusId)
    }

    @Test
    fun `should return empty optional`() {
        val id = 5555L

        assert { service.getImportStatus(id) }.returnedValue {
            isEmpty()
        }

        verify(mockRepository).findById(id)
    }
}
