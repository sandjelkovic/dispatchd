package com.sandjelkovic.dispatchd.contentservice.service

import arrow.core.Try
import com.sandjelkovic.dispatchd.contentservice.data.entity.Show

/**
 * @author sandjelkovic
 * @date 10.2.18.
 */
interface ContentRefreshService {
    fun updateAllContent(): Long
    fun updateContentIfNeeded(): Try<List<Show>>
}
