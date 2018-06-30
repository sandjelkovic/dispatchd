package com.sandjelkovic.dispatchd.contentservice.service

import arrow.core.Either
import arrow.core.Option
import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportStatus
import java.net.URI

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
interface ImportService {
    fun importFromUri(uri: URI): Either<ImportException, ImportStatus>

    fun getImportStatus(id: Long): Option<ImportStatus>
}
