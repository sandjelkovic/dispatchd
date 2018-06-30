package com.sandjelkovic.dispatchd.contentservice.web

import arrow.core.getOrElse
import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportStatus
import com.sandjelkovic.dispatchd.contentservice.service.ImportService
import com.sandjelkovic.dispatchd.contentservice.web.dto.ImportRequestDto
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
    fun newImport(@RequestBody requestBody: ImportRequestDto): ResponseEntity<Resource<ImportStatus>> {
        if (requestBody.mediaUrl.isBlank()) {
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
        return try {
            val uri = URI.create(requestBody.mediaUrl)!!
            importService.importFromUri(uri)
                    .map { ResponseEntity(Resource(it), HttpStatus.ACCEPTED) }
                    .toOption()
                    .getOrElse { ResponseEntity(HttpStatus.BAD_REQUEST) }
        } catch (e: IllegalArgumentException) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/{statusId}")
    fun getImportStatus(statusId: Long): ResponseEntity<Resource<ImportStatus>> =
            importService.getImportStatus(statusId)
                    .map { ResponseEntity(Resource(it), HttpStatus.OK) }
                    .getOrElse { ResponseEntity(HttpStatus.NOT_FOUND) }
}
