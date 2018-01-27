package com.sandjelkovic.dispatchd.contentservice.data.repository

import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*

/**
 * @author sandjelkovic
 * @date 27.1.18.
 */
interface ShowRepository : PagingAndSortingRepository<Show, Long> {
    fun findByTraktId(traktId: String): Optional<Show>

    fun findByTitle(title: String): List<Show>

    fun findByTitleContaining(title: String, pageable: Pageable): Page<Show>
}
