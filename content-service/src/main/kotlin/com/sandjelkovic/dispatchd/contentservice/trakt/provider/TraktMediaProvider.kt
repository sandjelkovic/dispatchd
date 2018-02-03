package com.sandjelkovic.dispatchd.contentservice.trakt.provider

import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowTrakt

/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
interface TraktMediaProvider {
    fun getShow(showId: String): ShowTrakt
}
