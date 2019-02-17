package com.sandjelkovic.dispatchd.contentservice.trakt.converter

import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import com.sandjelkovic.dispatchd.contentservice.trakt.dto.ShowTrakt
import org.springframework.core.convert.converter.Converter
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 14.4.18.
 */
class Trakt2ShowConverter : Converter<ShowTrakt, Show> {
    override fun convert(source: ShowTrakt): Show = Show().apply {
        title = source.title ?: ""
        description = source.overview ?: ""
        status = source.status ?: ""
        year = source.year
        lastLocalUpdate = ZonedDateTime.now()
        traktSlug = source.ids.getOrDefault("slug", "")
        traktId = source.ids.getOrDefault("trakt", "")
        imdbId = source.ids.getOrDefault("imdb", "")
        tvdbId = source.ids.getOrDefault("tvdb", "")
    }
}
