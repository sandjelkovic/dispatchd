package com.sandjelkovic.dispatchd.contentservice.service

import com.sandjelkovic.dispatchd.contentservice.data.entity.Show

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */
interface TraktImporter {

    fun importShow(showId: String): Show
}
