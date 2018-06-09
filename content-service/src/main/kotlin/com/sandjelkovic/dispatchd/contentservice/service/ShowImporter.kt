package com.sandjelkovic.dispatchd.contentservice.service

import arrow.core.Option
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import java.net.URI

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
interface ShowImporter {
    fun importShow(showId: String): Show
    fun supports(host: String): Boolean
    fun getIdentifier(uri: URI): Option<String>
}
