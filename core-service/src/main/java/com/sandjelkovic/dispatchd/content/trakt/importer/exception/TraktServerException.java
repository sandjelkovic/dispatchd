package com.sandjelkovic.dispatchd.content.trakt.importer.exception;


public class TraktServerException extends RuntimeException {

	public TraktServerException(String message) {
		super(message);
	}

	public TraktServerException(String message, Throwable cause) {
		super(message, cause);
	}

	public TraktServerException(Throwable cause) {
		super(cause);
	}

	public TraktServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TraktServerException() {
	}
}
