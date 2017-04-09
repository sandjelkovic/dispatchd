package com.sandjelkovic.dispatchd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShowNotFoundException extends ResourceNotFoundException {
	public ShowNotFoundException() {
	}

	public ShowNotFoundException(String message) {
		super(message);
	}

	public ShowNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShowNotFoundException(Throwable cause) {
		super(cause);
	}

	public ShowNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
