package com.sandjelkovic.dispatchd.contentservice.service

import arrow.core.Either
import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportStatus
import java.net.URI
import java.util.*

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
interface ImportService {
    fun importFromUri(uri: URI): Either<ImportException, ImportStatus>

    fun getImportStatus(id: Long): Optional<ImportStatus>
}
