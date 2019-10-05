package com.sandjelkovic.dispatchd.content.web

import arrow.core.*
import com.sandjelkovic.dispatchd.content.data.entity.ImportStatus
import com.sandjelkovic.dispatchd.content.service.ImportService
import com.sandjelkovic.dispatchd.content.web.dto.ImportDto
import com.sandjelkovic.dispatchd.content.web.dto.ImportStatusWebDto
import com.sandjelkovic.dispatchd.content.web.dto.toWebDto
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
    fun newImport(@RequestBody requestBody: ImportDto): ResponseEntity<Void> =
        validateImportRequest(requestBody)
            .flatMap { convertToUri(requestBody) }
            .flatMap(importService::importFromUri)
            .map { ResponseEntity.accepted().location(URI.create("/import/${it.value}")).build<Void>() }
            .getOrElse { ResponseEntity.badRequest().build<Void>() }

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
