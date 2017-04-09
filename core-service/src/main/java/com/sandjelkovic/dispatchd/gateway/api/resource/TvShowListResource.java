package com.sandjelkovic.dispatchd.gateway.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.gateway.api.dto.TvShowDTO;
import com.sandjelkovic.dispatchd.gateway.api.link.PageLinksAssembler;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@EqualsAndHashCode(callSuper = true)
public class TvShowListResource extends PageableListResource {
	@JsonProperty("_embedded")
	private List<TvShowResource> data;
	@JsonProperty("page")
	private PageMetadataResource pageMetadataResource;

	public TvShowListResource(Page<TvShowDTO> page, UriComponentsBuilder selfUrl) {
		super(new PageLinksAssembler(new PageMetadataResource(page), selfUrl));
		this.data = page.getContent().stream()
				.map(TvShowResource::new)
				.collect(toList());
		this.pageMetadataResource = new PageMetadataResource(page);
	}
}
