package com.sandjelkovic.dispatchd.exception;

public class IncompleteObjectForOperationException extends RuntimeException {
	public IncompleteObjectForOperationException() {
	}

	public IncompleteObjectForOperationException(String message) {
		super(message);
	}

	public IncompleteObjectForOperationException(Object obj) {
		super("Object <" + obj.toString() + "> is not fully formed for intended operation");
	}

	public IncompleteObjectForOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncompleteObjectForOperationException(Throwable cause) {
		super(cause);
	}

	public IncompleteObjectForOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
