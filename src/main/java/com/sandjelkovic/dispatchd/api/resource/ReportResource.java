package com.sandjelkovic.dispatchd.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.dto.ReportDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ReportResource extends ResourceSupport {

	@JsonProperty("_embedded")
	private final ReportDTO data;

}
