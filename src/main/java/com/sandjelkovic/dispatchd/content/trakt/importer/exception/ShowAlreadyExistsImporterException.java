package com.sandjelkovic.dispatchd.content.trakt.importer.exception;


public class ShowAlreadyExistsImporterException extends RuntimeException {

	public ShowAlreadyExistsImporterException(String message) {
		super(message);
	}

	public ShowAlreadyExistsImporterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShowAlreadyExistsImporterException(Throwable cause) {
		super(cause);
	}

	public ShowAlreadyExistsImporterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ShowAlreadyExistsImporterException() {
	}
}
