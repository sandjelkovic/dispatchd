package com.sandjelkovic.dispatchd.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.dto.EpisodeDTO;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class EpisodeListResource extends ResourceSupport {
	@JsonProperty("_embedded")
	private List<EpisodeResource> data;
	@JsonProperty("page")
	private PageMetadataResource pageMetadataResource;

	public EpisodeListResource(Page<EpisodeDTO> page) {
		this.data = page.getContent().stream()
				.map(EpisodeResource::new)
				.collect(toList());
		this.pageMetadataResource = new PageMetadataResource(page);
	}
}
