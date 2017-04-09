package com.sandjelkovic.dispatchd.gateway.api.processor;

import com.sandjelkovic.dispatchd.gateway.api.controller.ImportController;
import com.sandjelkovic.dispatchd.gateway.api.resource.ImportStatusResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author ${sandjelkovic}
 * @date 25.2.17.
 */
@Component
public class ImportStatusResourceProcessor implements ResourceProcessor<ImportStatusResource> {
	@Override
	public ImportStatusResource process(ImportStatusResource resource) {
		resource.add(linkTo(methodOn(ImportController.class).getImportStatus(resource.getData().getId()))
				.withSelfRel());
		return resource;
	}
}
