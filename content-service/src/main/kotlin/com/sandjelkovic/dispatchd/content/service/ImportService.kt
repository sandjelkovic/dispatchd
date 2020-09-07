package com.sandjelkovic.dispatchd.content.service

import arrow.core.Either
import arrow.core.Option
import arrow.core.flatMap
import com.sandjelkovic.dispatchd.content.data.entity.ImportProgressStatus
import com.sandjelkovic.dispatchd.content.data.entity.ImportProgressStatus.*
import com.sandjelkovic.dispatchd.content.data.entity.ImportStatus
import com.sandjelkovic.dispatchd.content.data.entity.ImportStatusId
import com.sandjelkovic.dispatchd.content.data.repository.ImportStatusRepository
import com.sandjelkovic.dispatchd.content.extensions.flatMapToOption
import java.net.URI

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
class ImportService(
    private val importStatusRepository: ImportStatusRepository,
    private val importerSelectionStrategy: ImporterSelectionStrategy,
    private val asyncService: SpringAsyncService
) {
    fun importFromUri(uri: URI): Either<ImportException, ImportStatusId> {
        val importStatus = importStatusRepository.save(ImportStatus(mediaUrl = uri.toString(), status = QUEUED))
        return importerSelectionStrategy.getImporter(uri)
            .flatMap { importer ->
                importer.getIdentifier(uri)
                    .map { asyncService.async { executeImport(importer, it, importStatus) } }
                    .map { ImportStatusId(importStatus.id!!) }
                    .toEither { InvalidImportUrlException() }
            }
    }

    private fun executeImport(importer: ShowImporter, showIdentifier: String, oldStatus: ImportStatus) =
        importStatusRepository.findById(oldStatus.id!!).orElse(oldStatus)
            .let { importStatusRepository.save(it.copy(status = IN_PROGRESS)) }
            .let { status ->
                importer.importShow(showIdentifier)
                    .fold(::mapExceptionToProgressStatus) { SUCCESS }
                    .let {
                        importStatusRepository.save(status.copy(status = it))
                    }
            }

    private fun mapExceptionToProgressStatus(exception: ImportException): ImportProgressStatus =
        when (exception) {
            is ShowAlreadyImportedException -> ERROR_SHOW_ALREADY_EXISTS
            is ShowDoesNotExistTraktException -> ERROR_REMOTE_SERVER
            is UnknownImportException -> ERROR
            is UnsupportedBackendException -> ERROR
            is InvalidImportUrlException -> ERROR
            is RemoteServiceException -> ERROR
        }

    fun getImportStatus(id: Long): Option<ImportStatus> = importStatusRepository.findById(id).flatMapToOption()
}
