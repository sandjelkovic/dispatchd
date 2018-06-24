package com.sandjelkovic.dispatchd.contentservice.service.impl

import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Service

/**
 * @author sandjelkovic
 * @date 24.6.18.
 */
@Service
class SpringAsyncService {
    @Async
    fun <T> async(block: () -> T): AsyncResult<T> {
        return AsyncResult(block())
    }
}
