package com.sandjelkovic.dispatchd

import assertk.Assert
import assertk.all
import assertk.assertions.isBetween
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import java.time.Duration
import java.time.ZonedDateTime
import java.util.*

/**
 * @author sandjelkovic
 * @date 21.4.18.
 */

fun <T> Assert<Optional<T>>.isPresent() {
    assertk.assert(actual.isPresent).isTrue()
}

fun <T> Assert<Optional<T>>.isEmpty() {
    assertk.assert(actual.isPresent).isFalse()
}

fun <T> Assert<Optional<T>>.isPresentAndExtracted(f: (T) -> Unit) {
    assertk.assert(actual).isPresent()
    actual.ifPresent { f(it) }
}

fun <T> Assert<Optional<T>>.isPresent(f: (Assert<T>) -> Unit) {
    assertk.assert(actual).isPresent()
    assert(actual.get()).all(f)
}

fun Assert<ZonedDateTime>.isInLast(duration: Duration) {
    assertk.assert(actual).isBetween(ZonedDateTime.now().minus(duration), ZonedDateTime.now())
}
