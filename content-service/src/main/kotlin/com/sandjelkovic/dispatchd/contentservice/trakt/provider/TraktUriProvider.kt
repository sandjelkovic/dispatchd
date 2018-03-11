package com.sandjelkovic.dispatchd.contentservice.trakt.provider

import java.net.URI
import java.time.LocalDate

/**
 * @author sandjelkovic
 * @date 11.3.18.
 */
interface TraktUriProvider {
    fun getShowUri(showId: String): URI
    fun getEpisodeUri(showId: String, seasonNumber: String, episodeNumber: String): URI
    fun getSeasonEpisodesUri(showId: String, seasonNumber: String): URI
    fun getSeasonsUri(showId: String): URI
    fun getSeasonsMinimalUri(showId: String): URI
    fun getSeasonEpisodesUri(fromDate: LocalDate): URI
}
