package com.sandjelkovic.dispatchd.gateway.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.gateway.api.dto.EpisodeDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EpisodeResource extends ResourceSupport {

	@JsonProperty("_embedded")
	private final EpisodeDTO data;
}
