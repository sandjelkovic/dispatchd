package com.sandjelkovic.dispatchd.content.service

import arrow.core.Try
import com.sandjelkovic.dispatchd.content.data.entity.Show

/**
 * @author sandjelkovic
 * @date 10.2.18.
 */
interface ContentRefreshService {
    fun updateAllContent(): Long
    fun updateContentIfStale(): Try<List<Show>>
}
