package com.sandjelkovic.dispatchd.content.trakt.converter

import com.sandjelkovic.dispatchd.content.data.entity.Season
import com.sandjelkovic.dispatchd.content.trakt.dto.SeasonTrakt
import org.springframework.core.convert.converter.Converter

/**
 * @author sandjelkovic
 * @date 14.4.18.
 */
class Trakt2SeasonConverter : Converter<SeasonTrakt, Season> {
    override fun convert(source: SeasonTrakt): Season = Season().apply {
        number = source.number
        description = source.overview ?: ""
        airDate = source.firstAired
        episodesCount = source.episodeCount ?: 0
        episodesAiredCount = source.airedEpisodes
        traktId = source.ids.getOrDefault("trakt", "")
        imdbId = source.ids.getOrDefault("imdb", "")
        tvdbId = source.ids.getOrDefault("tvdb", "")
    }
}
