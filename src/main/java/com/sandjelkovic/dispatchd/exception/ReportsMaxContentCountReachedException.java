package com.sandjelkovic.dispatchd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ${sandjelkovic}
 * @date 5.1.17.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReportsMaxContentCountReachedException extends RuntimeException {
}
