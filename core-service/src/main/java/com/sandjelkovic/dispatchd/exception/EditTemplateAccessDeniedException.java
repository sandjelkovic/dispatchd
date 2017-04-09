package com.sandjelkovic.dispatchd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class EditTemplateAccessDeniedException extends AccessDeniedException {

	public EditTemplateAccessDeniedException() {
		super("No rights to edit this template");
	}

	public EditTemplateAccessDeniedException(String msg) {
		super(msg);
	}

	public EditTemplateAccessDeniedException(String msg, Throwable t) {
		super(msg, t);
	}
}
