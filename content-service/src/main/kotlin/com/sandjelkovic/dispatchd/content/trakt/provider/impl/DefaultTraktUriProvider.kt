package com.sandjelkovic.dispatchd.content.trakt.provider.impl

import com.sandjelkovic.dispatchd.content.trakt.config.TraktProperties
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktUriProvider
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * @author sandjelkovic
 * @date 11.3.18.
 */
open class DefaultTraktUriProvider(val traktProperties: TraktProperties) : TraktUriProvider {
    companion object {
        const val extensionParameterName = "extended"
        const val extensionFullImages = "full,images"
        const val extensionMinimal = "min"
    }

    private val baseTraktUrl get() = traktProperties.baseServiceUrl

    override fun getShowUri(showId: String): URI {
        return UriComponentsBuilder.fromHttpUrl(baseTraktUrl)
            .pathSegment("shows")
            .pathSegment(showId)
            .queryParam(extensionParameterName, extensionFullImages)
            .build().encode().toUri()
    }

    override fun getEpisodeUri(showId: String, seasonNumber: String, episodeNumber: String): URI {
        return UriComponentsBuilder.fromHttpUrl(baseTraktUrl)
            .pathSegment("shows", showId)
            .pathSegment("seasons", seasonNumber)
            .pathSegment("episodes", episodeNumber)
            .queryParam(extensionParameterName, extensionFullImages)
            .build().encode().toUri()
    }

    override fun getSeasonEpisodesUri(showId: String, seasonNumber: String): URI {
        return UriComponentsBuilder.fromHttpUrl(baseTraktUrl)
            .pathSegment("shows", showId)
            .pathSegment("seasons", seasonNumber)
            .queryParam(extensionParameterName, extensionFullImages)
            .build().encode().toUri()
    }

    override fun getSeasonsUri(showId: String): URI {
        return UriComponentsBuilder.fromHttpUrl(baseTraktUrl)
            .pathSegment("shows", showId)
            .pathSegment("seasons")
            .queryParam(extensionParameterName, extensionFullImages)
            .build().encode().toUri()
    }

    override fun getSeasonsMinimalUri(showId: String): URI {
        return UriComponentsBuilder.fromHttpUrl(baseTraktUrl)
            .pathSegment("shows", showId)
            .pathSegment("seasons")
            .queryParam(extensionParameterName, extensionMinimal)
            .build().encode().toUri()
    }

    override fun getUpdatesUri(fromDate: LocalDate): URI {
        return UriComponentsBuilder.fromHttpUrl(baseTraktUrl)
            .pathSegment("shows")
            .pathSegment("updates")
            .pathSegment(fromDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
            .build().encode().toUri()
    }

}
