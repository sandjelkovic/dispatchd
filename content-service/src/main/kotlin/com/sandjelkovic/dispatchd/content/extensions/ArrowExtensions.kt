package com.sandjelkovic.dispatchd.content.extensions

import arrow.core.Option
import arrow.core.toOption
import java.util.*

/**
 * @author sandjelkovic
 * @date 21.4.18.
 */

fun <T> Optional<T>.flatMapToOption(): Option<T> = this.map { it.toOption() }.orElse(Option.empty())

