package com.sandjelkovic.dispatchd.contentservice.service.impl

import arrow.core.Either
import arrow.core.Option
import arrow.core.flatMap
import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportProgressStatus
import com.sandjelkovic.dispatchd.contentservice.data.entity.ImportStatus
import com.sandjelkovic.dispatchd.contentservice.data.repository.ImportStatusRepository
import com.sandjelkovic.dispatchd.contentservice.flatMapToOption
import com.sandjelkovic.dispatchd.contentservice.service.*
import java.net.URI

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
class DefaultImportService(private val importStatusRepository: ImportStatusRepository,
                           private val importerSelectionStrategy: ImporterSelectionStrategy,
                           private val asyncService: SpringAsyncService) : ImportService {
    override fun importFromUri(uri: URI): Either<ImportException, ImportStatus> {
        val importStatus = importStatusRepository.save(ImportStatus(mediaUrl = uri.toString(), status = ImportProgressStatus.QUEUED))
        return importerSelectionStrategy.getImporter(uri)
                .flatMap { importer ->
                    importer.getIdentifier(uri)
                            .map { asyncService.async { executeImport(importer, it, importStatus) } }
                            .map { importStatusRepository.findById(importStatus.id!!).orElse(importStatus) }
                            .toEither { InvalidImportUrlException() }
                }
    }

    private fun executeImport(importer: ShowImporter, it: String, oldStatus: ImportStatus) {
        val either = importer.importShow(it)
        val status = importStatusRepository.findById(oldStatus.id!!).orElse(oldStatus)

        val statusToSave: ImportProgressStatus = when (either) {
            is Either.Right -> ImportProgressStatus.SUCCESS
            is Either.Left -> when {
                either.a is UnknownImportException -> ImportProgressStatus.ERROR
                either.a is ShowAlreadyImportedException -> ImportProgressStatus.ERROR_SHOW_ALREADY_EXISTS
                either.a is ShowDoesNotExistTraktException -> ImportProgressStatus.ERROR_REMOTE_SERVER
                else -> ImportProgressStatus.ERROR
            }
        }
        importStatusRepository.save(status.copy(status = statusToSave))
    }

    override fun getImportStatus(id: Long): Option<ImportStatus> = importStatusRepository.findById(id).flatMapToOption()
}
