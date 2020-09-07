package com.sandjelkovic.dispatchd.content.service

import arrow.core.Either
import arrow.core.Option
import com.sandjelkovic.dispatchd.content.data.entity.Show
import java.net.URI

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
interface ShowImporter {
    fun importShow(showId: String): Either<ImportException, Show>
    fun refreshImportedShow(showId: String): Either<ImportException, Show>
    fun supports(host: String): Boolean
    fun getIdentifier(uri: URI): Option<String>
}
