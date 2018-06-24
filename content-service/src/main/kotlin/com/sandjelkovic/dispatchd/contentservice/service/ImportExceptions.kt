package com.sandjelkovic.dispatchd.contentservice.service

/**
 * @author sandjelkovic
 * @date 9.6.18.
 */
sealed class ImportException(message: String = "") : Throwable(message)

class UnsupportedBackendException : ImportException()
class InvalidImportUrlException : ImportException()
class UnknownImportException(message: String = "") : ImportException(message)
class ShowAlreadyImportedException : ImportException()
class ShowDoesNotExistTraktException : ImportException()
