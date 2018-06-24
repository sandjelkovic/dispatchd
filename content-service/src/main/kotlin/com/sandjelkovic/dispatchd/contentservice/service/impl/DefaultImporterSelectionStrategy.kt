package com.sandjelkovic.dispatchd.contentservice.service.impl

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.toOption
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import com.sandjelkovic.dispatchd.contentservice.service.*
import mu.KLogging
import java.net.URI

class DefaultImporterSelectionStrategy(private val showImporters: List<ShowImporter>) : ImporterSelectionStrategy {
    companion object : KLogging()

    override fun getImporter(uri: URI): Either<ImportException, () -> Show> =
            showImporters.firstOrNull { it.supports(uri.host) }.toOption()
                    .toEither { UnsupportedBackendException() }
                    .flatMap { importer ->
                        importer.getIdentifier(uri)
                                .map { identifier -> { importer.importShow(identifier) } }
                                .toEither { InvalidImportUrlException() }
                    }

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
