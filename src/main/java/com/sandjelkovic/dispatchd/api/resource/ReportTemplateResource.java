package com.sandjelkovic.dispatchd.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.dto.ReportTemplateDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Getter
@RequiredArgsConstructor
public class ReportTemplateResource extends ResourceSupport {

	@JsonProperty("_embedded")
	private final ReportTemplateDTO data;

}
