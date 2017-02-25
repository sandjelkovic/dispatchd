package com.sandjelkovic.dispatchd.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.controller.ImportRestController;
import com.sandjelkovic.dispatchd.api.dto.ImportStatusDto;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ImportStatusResource extends ResourceSupport {

	@JsonProperty("_embedded")
	private ImportStatusDto data;

	public ImportStatusResource(ImportStatusDto data) {
		this.data = data;
		this.add(ControllerLinkBuilder.linkTo(methodOn(ImportRestController.class).getImportStatus(data.getId())).withSelfRel());
	}

	public ImportStatusDto getData() {
		return data;
	}
}