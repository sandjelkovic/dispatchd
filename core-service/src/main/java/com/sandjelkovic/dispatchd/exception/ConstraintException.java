package com.sandjelkovic.dispatchd.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author ${sandjelkovic}
 * @date 16.2.17.
 */
@ResponseStatus(BAD_REQUEST)
public class ConstraintException extends RuntimeException {
}
