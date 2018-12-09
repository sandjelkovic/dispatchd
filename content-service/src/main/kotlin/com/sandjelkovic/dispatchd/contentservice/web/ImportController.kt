package com.sandjelkovic.dispatchd.contentservice.web

import arrow.core.*
import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportStatus
import com.sandjelkovic.dispatchd.contentservice.service.ImportService
import com.sandjelkovic.dispatchd.contentservice.web.dto.ImportDto
import com.sandjelkovic.dispatchd.contentservice.web.dto.ImportStatusWebDto
import com.sandjelkovic.dispatchd.contentservice.web.dto.toWebDto
import org.springframework.hateoas.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

/**
 * @author sandjelkovic
 * @date 24.6.18.
 */
@RestController
@RequestMapping(value = ["/import"])
class ImportController(private val importService: ImportService) {
    @PostMapping
    fun newImport(@RequestBody requestBody: ImportDto): ResponseEntity<Resource<ImportStatusWebDto>> =
        validateImportRequest(requestBody)
            .flatMap { convertToUri(requestBody) }
            .flatMap(importService::importFromUri)
            .map(ImportStatus::toWebDto)
            .map { ResponseEntity(Resource(it), HttpStatus.ACCEPTED) }
            .getOrElse { ResponseEntity(HttpStatus.BAD_REQUEST) }

    private fun validateImportRequest(requestBody: ImportDto): Either<Unit, String> =
        requestBody.mediaUrl.toOption()
            .filter { it.isNotBlank() }
            .toEither { }

    private fun convertToUri(requestBody: ImportDto) =
        Try { URI.create(requestBody.mediaUrl) }
            .filter { it != null }
            .toEither()

    @GetMapping("/{statusId}")
    fun getImportStatus(@PathVariable statusId: Long): ResponseEntity<Resource<ImportStatusWebDto>> =
        importService.getImportStatus(statusId)
            .map(ImportStatus::toWebDto)
            .map { ResponseEntity(Resource(it), HttpStatus.OK) }
            .getOrElse { ResponseEntity(HttpStatus.NOT_FOUND) }
}
