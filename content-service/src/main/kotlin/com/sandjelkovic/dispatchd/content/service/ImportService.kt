package com.sandjelkovic.dispatchd.content.service

import arrow.core.Either
import arrow.core.Option
import com.sandjelkovic.dispatchd.content.data.entity.ImportStatus
import com.sandjelkovic.dispatchd.content.data.entity.ImportStatusId
import java.net.URI

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
interface ImportService {
    fun importFromUri(uri: URI): Either<ImportException, ImportStatusId>

    fun getImportStatus(id: Long): Option<ImportStatus>
}
