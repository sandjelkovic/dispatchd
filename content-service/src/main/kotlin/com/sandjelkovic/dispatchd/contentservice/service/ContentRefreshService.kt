package com.sandjelkovic.dispatchd.contentservice.service

import arrow.core.Either

/**
 * @author sandjelkovic
 * @date 10.2.18.
 */
interface ContentRefreshService {
    fun updateAllContent(): Long
    fun updateContentIfNeeded(): Either<RemoteServiceException, Int>
}
