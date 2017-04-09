package com.sandjelkovic.dispatchd.gateway.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceProcessorInvoker;
import org.springframework.stereotype.Component;

/**
 * @author ${sandjelkovic}
 * @date 26.2.17.
 */
@Component
public class BaseController {
	@Autowired
	protected ResourceProcessorInvoker resourceProcessorInvoker;
}
