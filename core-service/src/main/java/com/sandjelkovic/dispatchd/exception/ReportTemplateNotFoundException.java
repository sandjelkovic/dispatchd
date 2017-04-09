package com.sandjelkovic.dispatchd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReportTemplateNotFoundException extends ResourceNotFoundException {
	public ReportTemplateNotFoundException() {
	}

	public ReportTemplateNotFoundException(String message) {
		super(message);
	}

	public ReportTemplateNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReportTemplateNotFoundException(Throwable cause) {
		super(cause);
	}

	public ReportTemplateNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
