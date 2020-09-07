package com.sandjelkovic.dispatchd.content.service

import arrow.core.Either
import arrow.core.Try
import arrow.core.getOrElse
import arrow.core.toOption
import mu.KLogging
import java.net.URI

open class ImporterSelectionStrategy(private val showImporters: List<ShowImporter>) {
    companion object : KLogging()

    fun getImporter(uri: URI): Either<ImportException, ShowImporter> =
        showImporters.firstOrNull { importer ->
            Try { importer.supports(uri.host) }.getOrElse { false }
        }.toOption().toEither { UnsupportedBackendException() }

//            override fun getImporter(uri: URI): Option<() -> Show> =
//            Option.fromNullable(showImporters.firstOrNull { it.supports(uri.host) })
//                    .flatMap { importer ->
//                        importer.getIdentifier(uri).map {
//                            { importer.importShow(it) }
//                        }
//                    }

//    override fun getImporter(uri: URI): () -> Show =
//            showImporters
//                    .firstOrNull { it.supports(uri.host) }
//                    ?.let { importer ->
//                        { importer.importShow(importer.getIdentifier(uri)) }
//                    }
//                    ?: { throw UnsupportedBackendException() }
}
