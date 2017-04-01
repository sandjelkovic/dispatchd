package com.sandjelkovic.dispatchd.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author sandjelkovic
 * @date 1.4.17.
 */
@ResponseStatus(BAD_REQUEST)
public class ExistingRelationException extends RuntimeException {
	public ExistingRelationException() {
	}

	public ExistingRelationException(String s) {
		super(s);
	}

	public ExistingRelationException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public ExistingRelationException(Throwable throwable) {
		super(throwable);
	}

	public ExistingRelationException(String s, Throwable throwable, boolean b, boolean b1) {
		super(s, throwable, b, b1);
	}
}
