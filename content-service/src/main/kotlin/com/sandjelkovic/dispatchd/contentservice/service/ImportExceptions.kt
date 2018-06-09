package com.sandjelkovic.dispatchd.contentservice.service

/**
 * @author sandjelkovic
 * @date 9.6.18.
 */
sealed class ImportException : Throwable()

class UnsupportedBackendException : ImportException()
class InvalidImportUrlException : ImportException()
