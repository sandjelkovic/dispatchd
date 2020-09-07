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
import org.springframework.core.task.TaskExecutor
import java.net.URI
import java.util.concurrent.CompletableFuture

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
open class ImportService(
    private val importStatusRepository: ImportStatusRepository,
    private val importerSelectionStrategy: ImporterSelectionStrategy,
    private val showImportTaskExecutor: TaskExecutor
) {
    fun importFromUri(uri: URI): Either<ImportException, ImportStatusId> {
        val importStatus = importStatusRepository.save(ImportStatus(mediaUrl = uri.toString(), status = QUEUED))
        // everything should be async
        return importerSelectionStrategy.getImporter(uri)
            .flatMap { importer ->
                importer.getIdentifier(uri)
                    .toEither { InvalidImportUrlException() }
                    .map { CompletableFuture.runAsync({ executeImport(importer, it, importStatus) }, showImportTaskExecutor) }
                    .map { ImportStatusId(importStatus.id!!) }
            }
    }

    private fun executeImport(importer: ShowImporter, showIdentifier: String, oldStatus: ImportStatus): ImportStatus =
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
