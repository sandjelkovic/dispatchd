package com.sandjelkovic.dispatchd.contentservice

import java.time.Duration
import java.time.ZonedDateTime

/**
 * @author sandjelkovic
 * @date 21.4.18.
 */

object ago

val Int.seconds: Duration
    get() = Duration.ofSeconds(toLong())
val Long.seconds: Duration
    get() = Duration.ofSeconds(this)

fun Int.minutes() = Duration.ofMinutes(this.toLong())

infix fun Int.minutes(ago: ago) = ZonedDateTime.now().minus(Duration.ofMinutes(this.toLong()))

infix fun Duration.before(time: ZonedDateTime) = time.minus(this)

infix fun Duration.and(other: Duration) = plus(other)


val Number.hours: Duration
    get() = Duration.ofHours(this.toLong())
