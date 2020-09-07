package com.sandjelkovic.dispatchd.content.web

import arrow.core.Either
import com.sandjelkovic.dispatchd.content.service.ImportService
import com.sandjelkovic.dispatchd.content.service.InvalidImportUrlException
import com.sandjelkovic.dispatchd.content.web.dto.ImportDto
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.springframework.http.HttpStatus
import strikt.api.expectThat
import strikt.assertions.isEqualTo

/**
 * @author sandjelkovic
 * @date 24.6.18.
 */
class ImportControllerUnitTest {

    private val importService: ImportService = mockk {
        every { importFromUri(any()) } returns Either.Left(InvalidImportUrlException())
    }

    @Test
    fun `Should return Bad Request on import when request doesn't sent a URL`() {
        val importController = ImportController(importService)

        expectThat(importController.newImport(ImportDto("random string")).statusCode)
            .isEqualTo(HttpStatus.BAD_REQUEST)

        expectThat(importController.newImport(ImportDto("")).statusCode)
            .isEqualTo(HttpStatus.BAD_REQUEST)
    }
}
