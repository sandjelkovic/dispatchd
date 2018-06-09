package com.sandjelkovic.dispatchd.contentservice.service

import arrow.core.Either
import arrow.core.Option
import arrow.core.flatMap
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import mu.KLogging
import java.net.URI

class DefaultImportStrategy(val showImporters: List<ShowImporter>) : ImportStrategy {
    companion object : KLogging()

    override fun getImporter(uri: URI): Either<ImportException, () -> Show> =
            Option.fromNullable(showImporters.firstOrNull { it.supports(uri.host) })
                    .toEither { UnsupportedBackendException() }
                    .flatMap { importer ->
                        importer.getIdentifier(uri)
                                .map { { importer.importShow(it) } }
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
