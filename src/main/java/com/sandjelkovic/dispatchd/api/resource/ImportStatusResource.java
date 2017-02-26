package com.sandjelkovic.dispatchd.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.dto.ImportStatusDto;
import org.springframework.hateoas.ResourceSupport;

public class ImportStatusResource extends ResourceSupport {

	@JsonProperty("_embedded")
	private ImportStatusDto data;

	public ImportStatusResource(ImportStatusDto data) {
		this.data = data;
	}

	public ImportStatusDto getData() {
		return data;
	}
}
