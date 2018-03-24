package com.sandjelkovic.dispatchd.contentservice.trakt.provider.impl

import com.sandjelkovic.dispatchd.contentservice.trakt.dto.EpisodeTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.SeasonTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowTrakt
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktMediaProvider
import com.sandjelkovic.dispatchd.contentservice.trakt.provider.TraktUriProvider
import mu.KLogging
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.util.*

/**
 * @author sandjelkovic
 * @date 28.1.18.
 */
@Service
class DefaultTraktMediaProvider(private val traktUriProvider: TraktUriProvider,
                                private val traktRestTemplate: RestTemplate) : TraktMediaProvider {
    companion object : KLogging()

    override fun getShow(showId: String): Optional<ShowTrakt> {
        val uri = traktUriProvider.getShowUri(showId)
        val show: ShowTrakt? = traktRestTemplate.getForObject(uri)
        logger.debug("Retrieved Show: $show")
        return Optional.ofNullable(show)
    }

    override fun getSeasons(showId: String): List<SeasonTrakt> {
        val uri = traktUriProvider.getSeasonsUri(showId)
        val seasons: Array<SeasonTrakt>? = traktRestTemplate.getForObject(uri)
        logger.debug("Retrieved Seasons: $seasons")
        return seasons?.toList() ?: listOf()
    }

    override fun getSeasonsMinimal(showId: String): List<SeasonTrakt> {
        val uri = traktUriProvider.getSeasonsMinimalUri(showId)
        val seasons: Array<SeasonTrakt>? = traktRestTemplate.getForObject(uri)
        logger.debug("Retrieved minimal Seasons: $seasons")
        return seasons?.toList() ?: listOf()
    }

    override fun getShowEpisodes(showId: String): List<EpisodeTrakt> {
        return this.getSeasonsMinimal(showId)
                .map { season -> this.getSeasonEpisodes(showId, season.number ?: "") }
                .flatMap { it.toList() }
    }

    override fun getSeasonEpisodes(showId: String, seasonNumber: String): List<EpisodeTrakt> {
        val uri = traktUriProvider.getSeasonEpisodesUri(showId, seasonNumber)
        val episodes: Array<EpisodeTrakt>? = traktRestTemplate.getForObject(uri)
        logger.debug("Retrieved Season's Episodes: $episodes")
        return episodes?.toList() ?: listOf()
    }

    override fun getSeasonsAsync(showId: String): AsyncResult<List<SeasonTrakt>> =
            AsyncResult(this.getSeasons(showId))

    override fun getShowEpisodesAsync(showId: String): AsyncResult<List<EpisodeTrakt>> =
            AsyncResult(this.getShowEpisodes(showId))
}
