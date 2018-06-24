package com.sandjelkovic.dispatchd.contentservice.service.impl

import arrow.core.Either
import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportProgressStatus
import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportStatus
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import com.sandjelkovic.dispatchd.contentservice.data.repository.ImportStatusRepository
import com.sandjelkovic.dispatchd.contentservice.service.ImportException
import com.sandjelkovic.dispatchd.contentservice.service.ImportService
import com.sandjelkovic.dispatchd.contentservice.service.ImporterSelectionStrategy
import java.net.URI
import java.util.*

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
class DefaultImportService(val importStatusRepository: ImportStatusRepository, val importerSelectionStrategy: ImporterSelectionStrategy) : ImportService {
    override fun importFromUri(uri: URI): Either<ImportException, ImportStatus> {
        val importStatus = importStatusRepository.save(ImportStatus(mediaUrl = uri.toString(), status = ImportProgressStatus.QUEUED))
        return importerSelectionStrategy.getImporter(uri)
                .map {
                    startImport(it)
                    importStatusRepository.findById(importStatus.id!!).orElse(importStatus)
                }
    }

    private fun startImport(f: () -> Show) = f() // should be async

    override fun getImportStatus(id: Long): Optional<ImportStatus> = importStatusRepository.findById(id)
}
