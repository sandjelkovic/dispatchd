package com.sandjelkovic.dispatchd.contentservice.web

import arrow.core.Either
import assertk.assertions.isEqualTo
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.sandjelkovic.dispatchd.contentservice.service.ImportService
import com.sandjelkovic.dispatchd.contentservice.service.InvalidImportUrlException
import com.sandjelkovic.dispatchd.contentservice.web.dto.ImportRequestDto
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * @author sandjelkovic
 * @date 24.6.18.
 */
class ImportControllerTest {

    private val importService: ImportService = mock {
        on { importFromUri(any()) } doReturn Either.Left(InvalidImportUrlException())
    }

    @Test
    fun `Should return Bad Request on import when request doesn't sent a URL`() {
        val importController = ImportController(importService)

        assertk.assert {
            val newImport = importController.newImport(ImportRequestDto("random string"))
            newImport.statusCode
        }.returnedValue {
            isEqualTo(HttpStatus.BAD_REQUEST)
        }

        assertk.assert { importController.newImport(ImportRequestDto("")).statusCode }.returnedValue {
            isEqualTo(HttpStatus.BAD_REQUEST)
        }
    }
}
