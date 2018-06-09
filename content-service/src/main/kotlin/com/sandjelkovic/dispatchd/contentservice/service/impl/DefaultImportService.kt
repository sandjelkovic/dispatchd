package com.sandjelkovic.dispatchd.contentservice.service.impl

import arrow.core.Either
import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportProgressStatus
import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportStatus
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import com.sandjelkovic.dispatchd.contentservice.data.repository.ImportStatusRepository
import com.sandjelkovic.dispatchd.contentservice.service.ImportService
import com.sandjelkovic.dispatchd.contentservice.service.ImportStrategy
import java.net.URI
import java.util.*

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
class DefaultImportService(val importStatusRepository: ImportStatusRepository, val importStrategy: ImportStrategy) : ImportService {
    override fun importFromUri(uri: URI): ImportStatus {
        val importStatus = importStatusRepository.save(ImportStatus(mediaUrl = uri.toString(), status = ImportProgressStatus.QUEUED))
        val importer = importStrategy.getImporter(uri)
        when (importer) {
            is Either.Left -> {
                importStatusRepository.save(importStatus.copy(status = ImportProgressStatus.ERROR))
            }
            is Either.Right -> {
                startImport(importer.b)
            }
        }
        return importStatus
    }

    private fun startImport(f: () -> Show) = f() // should be async

    override fun getImportStatus(id: Long): Optional<ImportStatus> = importStatusRepository.findById(id)
}
