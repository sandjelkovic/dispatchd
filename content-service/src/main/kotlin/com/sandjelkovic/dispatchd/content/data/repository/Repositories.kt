package com.sandjelkovic.dispatchd.content.data.repository

import com.sandjelkovic.dispatchd.content.data.entity.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*
import java.util.stream.Stream

/**
 * @author sandjelkovic
 * @date 1.8.18.
 */
interface EpisodeRepository : PagingAndSortingRepository<Episode, Long> {
    fun findByShow(show: Show, pageable: Pageable): Page<Episode>

    fun findByShow(show: Show): Stream<Episode>

    fun findBySeason(season: Season, pageable: Pageable): Page<Episode>

    fun findBySeason(season: Season): Stream<Episode>

    fun findBySeason_Show_IdAndSeason_Number(showId: Long, seasonNumber: String): Stream<Episode>
}

interface SeasonRepository : PagingAndSortingRepository<Season, Long> {
    fun findByShow(show: Show): Stream<Season>

    fun findByShow(show: Show, pageable: Pageable): Page<Season>
}

interface ShowRepository : PagingAndSortingRepository<Show, Long> {
    fun findByTraktId(traktId: String): Optional<Show>

    fun findByTraktSlug(traktSlug: String): Optional<Show>

    fun findByTitle(title: String): Stream<Show>

    fun findByTitleContaining(title: String, pageable: Pageable): Page<Show>
}

interface ImportStatusRepository : PagingAndSortingRepository<ImportStatus, Long>

interface UpdateJobRepository : PagingAndSortingRepository<UpdateJob, Long> {
    /**
     * First by Desc -> Last entry
     */
    fun findFirstByOrderByFinishTimeDesc(): Optional<UpdateJob>

    /**
     * First by Desc -> Last entry
     */
    fun findFirstByOrderByStartTimeDesc(): Optional<UpdateJob>

    fun findFirstBySuccessOrderByFinishTimeDesc(success: Boolean): Optional<UpdateJob>
}
