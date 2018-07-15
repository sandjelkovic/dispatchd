package com.sandjelkovic.dispatchd.contentservice.web

import arrow.core.getOrElse
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
    fun newImport(@RequestBody requestBody: ImportDto): ResponseEntity<Resource<ImportStatusWebDto>> {
        if (requestBody.mediaUrl.isBlank()) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        return try {
            val uri = URI.create(requestBody.mediaUrl)!!
            importService.importFromUri(uri)
                    .map { ResponseEntity(Resource(it.toWebDto()), HttpStatus.ACCEPTED) }
                    .toOption()
                    .getOrElse { ResponseEntity(HttpStatus.BAD_REQUEST) }
        } catch (e: IllegalArgumentException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/{statusId}")
    fun getImportStatus(@PathVariable statusId: Long): ResponseEntity<Resource<ImportStatusWebDto>> =
            importService.getImportStatus(statusId)
                    .map { ResponseEntity(Resource(it.toWebDto()), HttpStatus.OK) }
                    .getOrElse { ResponseEntity(HttpStatus.NOT_FOUND) }
}
