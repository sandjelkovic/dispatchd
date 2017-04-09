package com.sandjelkovic.dispatchd.gateway.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.gateway.api.dto.ReportTemplateDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReportTemplateResource extends ResourceSupport {

	@JsonProperty("_embedded")
	private final ReportTemplateDTO data;

}
