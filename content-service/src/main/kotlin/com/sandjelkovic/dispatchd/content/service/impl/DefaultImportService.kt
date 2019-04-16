package com.sandjelkovic.dispatchd.content.service.impl

import arrow.core.Either
import arrow.core.Option
import arrow.core.flatMap
import com.sandjelkovic.dispatchd.content.data.entity.ImportProgressStatus
import com.sandjelkovic.dispatchd.content.data.entity.ImportProgressStatus.*
import com.sandjelkovic.dispatchd.content.data.entity.ImportStatus
import com.sandjelkovic.dispatchd.content.data.repository.ImportStatusRepository
import com.sandjelkovic.dispatchd.content.flatMapToOption
import com.sandjelkovic.dispatchd.content.service.*
import java.net.URI

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
class DefaultImportService(private val importStatusRepository: ImportStatusRepository,
                           private val importerSelectionStrategy: ImporterSelectionStrategy,
                           private val asyncService: SpringAsyncService) : ImportService {
    override fun importFromUri(uri: URI): Either<ImportException, ImportStatus> {
        val importStatus = importStatusRepository.save(ImportStatus(mediaUrl = uri.toString(), status = QUEUED))
        return importerSelectionStrategy.getImporter(uri)
                .flatMap { importer ->
                    importer.getIdentifier(uri)
                            .map { asyncService.async { executeImport(importer, it, importStatus) } }
                            .map { importStatusRepository.findById(importStatus.id!!).orElse(importStatus) }
                            .toEither { InvalidImportUrlException() }
                }
    }

    private fun executeImport(importer: ShowImporter, showIdentifier: String, oldStatus: ImportStatus) =
            importStatusRepository.findById(oldStatus.id!!).orElse(oldStatus)
                    .let { status ->
                        importer.importShow(showIdentifier)
                                .fold(::mapExceptionToProgressStatus) { SUCCESS }
                                .let {
                                    importStatusRepository.save(status.copy(status = it))
                                }
                    }

    private fun mapExceptionToProgressStatus(exception: ImportException): ImportProgressStatus =
            when (exception) {
                is UnknownImportException -> ERROR
                is ShowAlreadyImportedException -> ERROR_SHOW_ALREADY_EXISTS
                is ShowDoesNotExistTraktException -> ERROR_REMOTE_SERVER
                else -> ERROR
            }

    override fun getImportStatus(id: Long): Option<ImportStatus> = importStatusRepository.findById(id).flatMapToOption()
}
