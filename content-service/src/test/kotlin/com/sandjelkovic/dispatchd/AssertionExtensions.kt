package com.sandjelkovic.dispatchd

import arrow.core.Option
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
    assertk.assert(actual.get(), name = name).all(f)
}

fun Assert<ZonedDateTime>.isInLast(duration: Duration) {
    assertk.assert(actual).isBetween(ZonedDateTime.now().minus(duration), ZonedDateTime.now())
}

fun <T> Assert<Option<T>>.isNotEmpty(f: (Assert<T>) -> Unit = {}) {
    assertk.assert(actual.isDefined()).isTrue()
    assertk.assert(actual.orNull()!!, name = name).all(f)
}

// f is never used because Generics don't distinguish between Assert<Optional<T>> and  Assert<Option<T>> ... That's why we need f as just a marker to change the method signature
fun <T> Assert<Option<T>>.isEmpty(f: (Assert<T>) -> Unit = {}) {
    assertk.assert(actual.isDefined()).isFalse()
}
