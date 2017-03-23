package com.sandjelkovic.dispatchd.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.dto.TvShowDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Getter
@RequiredArgsConstructor
public class TvShowResource extends ResourceSupport {

	@JsonProperty("_embedded")
	private final TvShowDTO data;
}
