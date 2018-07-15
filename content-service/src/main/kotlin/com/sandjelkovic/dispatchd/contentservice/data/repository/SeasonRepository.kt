package com.sandjelkovic.dispatchd.contentservice.data.repository

import com.sandjelkovic.dispatchd.contentservice.data.entity.Season
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

/**
 * @author sandjelkovic
 * @date 27.1.18.
 */
interface SeasonRepository : PagingAndSortingRepository<Season, Long> {
    fun findByShow(show: Show): List<Season>

    fun findByShow(show: Show, pageable: Pageable): Page<Season>
}
