package com.sandjelkovic.dispatchd.content.extensions

import org.springframework.core.convert.ConversionService

/**
 * @author sandjelkovic
 * @date 24.3.18.
 */

inline fun <reified T> ConversionService.convert(source: Any): T = this.convert(source, T::class.java)!!
