package com.sandjelkovic.dispatchd.contentservice.service

import arrow.core.Either
import java.net.URI

/**
 * @author sandjelkovic
 * @date 9.6.18.
 */
interface ImporterSelectionStrategy {
    fun getImporter(uri: URI): Either<ImportException, ShowImporter>
}
