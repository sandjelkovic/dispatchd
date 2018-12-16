package com.sandjelkovic.dispatchd.contentservice.web

import arrow.core.Try
import arrow.core.getOrElse
import arrow.data.Validated
import arrow.data.invalid
import com.sandjelkovic.dispatchd.contentservice.data.entity.Episode
import com.sandjelkovic.dispatchd.contentservice.data.entity.Season
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import com.sandjelkovic.dispatchd.contentservice.data.repository.EpisodeRepository
import com.sandjelkovic.dispatchd.contentservice.data.repository.SeasonRepository
import com.sandjelkovic.dispatchd.contentservice.data.repository.ShowRepository
import com.sandjelkovic.dispatchd.contentservice.flatMapToOption
import com.sandjelkovic.dispatchd.contentservice.web.dto.SeasonDto
import com.sandjelkovic.dispatchd.contentservice.web.dto.ShowDto
import com.sandjelkovic.dispatchd.contentservice.web.dto.toDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.Resource
import org.springframework.hateoas.Resources
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors.toList

/**
 * @author sandjelkovic
 * @date 2018-12-08
 */

@RestController
@RequestMapping("/shows")
class ShowController(
    val episodeRepository: EpisodeRepository,
    val seasonRepository: SeasonRepository,
    val showRepository: ShowRepository
) {
    @GetMapping
    fun shows(page: Pageable): ResponseEntity<Resource<Page<ShowDto>>> = showRepository.findAll(page)
        .map(Show::toDto)
        .let { okResponse(Resource(it)) }

    @GetMapping("/{showId}")
    fun show(@PathVariable showId: String) = getShow(showId)
        .map { Resource(it.toDto()) }
        .map(this::okResponse)
        .getOrElse { ResponseEntity.notFound().build() }


    @GetMapping("/{showId}/seasons")
    fun seasons(@PathVariable showId: String): ResponseEntity<Resources<SeasonDto>> =
    //TODO Validate show Id
        getShow(showId)
            .map(seasonRepository::findByShow)
            .map { it.map(Season::toDto).collect(toList()) }
            .map { Resources(it) }
            .map(this::okResponse)
            .getOrElse { ResponseEntity.notFound().build() }

    @GetMapping("/{showId}/seasons/{seasonNumber}/episodes")
    fun episodes(@PathVariable showId: String, @PathVariable seasonNumber: String) {
        getShow(showId)
            .map { episodeRepository.findBySeason_Show_IdAndSeason_Number(it.id!!, seasonNumber) }
            .map { it.map(Episode::toDto).collect(toList()) }
            .map { Resources(it) }
            .map(this::okResponse)
            .getOrElse { ResponseEntity.notFound().build() }
    }

    private fun getShow(showId: String) = validateShowId(showId)
        .map { showRepository.findById(it) }
        .map { it.flatMapToOption() }
        .toOption()
        .flatMap { it }

    private fun validateShowId(showId: String): Validated<Throwable, Long> =
        Try { showId.toLong() }
            .fold(
                { it.invalid() },
                { Validated.Valid(it) }
            )

    private fun <T> okResponse(payload: T) = ResponseEntity.ok().body(payload)
}
