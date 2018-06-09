package com.sandjelkovic.dispatchd.contentservice.service

import arrow.core.Either
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import java.net.URI

/**
 * @author sandjelkovic
 * @date 9.6.18.
 */
interface ImportStrategy {
    fun getImporter(uri: URI): Either<ImportException, () -> Show>
}
