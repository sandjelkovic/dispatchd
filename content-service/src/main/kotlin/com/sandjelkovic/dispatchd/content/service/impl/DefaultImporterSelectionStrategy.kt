package com.sandjelkovic.dispatchd.content.service.impl

import arrow.core.Either
import arrow.core.Try
import arrow.core.getOrElse
import arrow.core.toOption
import com.sandjelkovic.dispatchd.content.service.ImportException
import com.sandjelkovic.dispatchd.content.service.ImporterSelectionStrategy
import com.sandjelkovic.dispatchd.content.service.ShowImporter
import com.sandjelkovic.dispatchd.content.service.UnsupportedBackendException
import mu.KLogging
import java.net.URI

class DefaultImporterSelectionStrategy(private val showImporters: List<ShowImporter>) : ImporterSelectionStrategy {
    companion object : KLogging()

    override fun getImporter(uri: URI): Either<ImportException, ShowImporter> =
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
