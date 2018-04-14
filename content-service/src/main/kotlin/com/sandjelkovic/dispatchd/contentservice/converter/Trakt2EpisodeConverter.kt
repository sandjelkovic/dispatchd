package com.sandjelkovic.dispatchd.contentservice.converter

import com.sandjelkovic.dispatchd.contentservice.data.entity.Episode
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.EpisodeTrakt
import org.springframework.core.convert.converter.Converter

/**
 * @author sandjelkovic
 * @date 14.4.18.
 */
class Trakt2EpisodeConverter : Converter<EpisodeTrakt, Episode> {
    override fun convert(source: EpisodeTrakt): Episode = Episode().apply {
        title = source.title.orEmpty()
        description = source.overview.orEmpty()
        number = source.number ?: 0
        airDate = source.firstAired
        seasonNumber = source.season
        traktId = source.ids.getOrDefault("trakt", "")
        imdbId = source.ids.getOrDefault("imdb", "")
        tvdbId = source.ids.getOrDefault("tvdb", "")
        lastUpdated = source.updatedAt
    }
}
