package com.sandjelkovic.dispatchd.contentservice

import java.time.Duration

/**
 * @author sandjelkovic
 * @date 21.4.18.
 */

val Int.seconds: Duration
    get() = Duration.ofSeconds(toLong())
val Long.seconds: Duration
    get() = Duration.ofSeconds(this)
