package com.sandjelkovic.dispatchd.contentservice.service.impl

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

/**
 * @author sandjelkovic
 * @date 24.6.18.
 */
@Service
class SpringAsyncService {
    @Async
    fun <T> async(block: () -> T): CompletableFuture<T> = CompletableFuture.completedFuture(block())!!
}
