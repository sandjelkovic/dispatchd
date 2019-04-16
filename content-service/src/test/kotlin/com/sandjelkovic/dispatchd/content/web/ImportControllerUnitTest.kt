package com.sandjelkovic.dispatchd.content.web

import arrow.core.Either
import assertk.assertions.isEqualTo
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.sandjelkovic.dispatchd.content.service.ImportService
import com.sandjelkovic.dispatchd.content.service.InvalidImportUrlException
import com.sandjelkovic.dispatchd.content.web.dto.ImportDto
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * @author sandjelkovic
 * @date 24.6.18.
 */
class ImportControllerUnitTest {

    private val importService: ImportService = mock {
        on { importFromUri(any()) } doReturn Either.Left(InvalidImportUrlException())
    }

    @Test
    fun `Should return Bad Request on import when request doesn't sent a URL`() {
        val importController = ImportController(importService)

        assertk.assert {
            val newImport = importController.newImport(ImportDto("random string"))
            newImport.statusCode
        }.returnedValue {
            isEqualTo(HttpStatus.BAD_REQUEST)
        }

        assertk.assert { importController.newImport(ImportDto("")).statusCode }.returnedValue {
            isEqualTo(HttpStatus.BAD_REQUEST)
        }
    }
}
