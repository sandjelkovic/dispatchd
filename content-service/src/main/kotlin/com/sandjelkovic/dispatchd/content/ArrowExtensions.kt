package com.sandjelkovic.dispatchd.content

import arrow.core.Option
import arrow.core.toOption
import java.util.*

/**
 * @author sandjelkovic
 * @date 21.4.18.
 */

inline fun <T> Optional<T>.flatMapToOption(): Option<T> = this.map { it.toOption() }.orElse(Option.empty())

