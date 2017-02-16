package com.sandjelkovic.dispatchd.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.controller.ContentController;
import com.sandjelkovic.dispatchd.api.dto.EpisodeDTO;
import com.sandjelkovic.dispatchd.api.link.PageLinksAssembler;
import com.sandjelkovic.dispatchd.api.link.RelNamesConstants;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class EpisodeListResource extends ResourceSupport {
	@JsonProperty("_embedded")
	private List<EpisodeResource> data;
	@JsonProperty("page")
	private PageMetadataResource pageMetadataResource;

	public EpisodeListResource(Page<EpisodeDTO> page) {
		UriComponentsBuilder baseContentLink = linkTo(ContentController.class).toUriComponentsBuilder();

		this.data = page.getContent().stream()
				.map(EpisodeResource::new)
				.collect(toList());
		this.pageMetadataResource = new PageMetadataResource(page);

		this.add(getShowsEpisodesLink(page, baseContentLink).withRel(RelNamesConstants.SHOW_EPISODES));
		this.add(getSeasonsEpisodesLink(page, baseContentLink).withRel(RelNamesConstants.SEASON_EPISODES));
	}

	private Link getSeasonsEpisodesLink(Page<EpisodeDTO> page, UriComponentsBuilder baseContentLink) {
		UriComponentsBuilder uri = baseContentLink.cloneBuilder()
				.pathSegment("seasons")
				.pathSegment("{seasonId}")
				.pathSegment("episodes");
		return new PageLinksAssembler(new PageMetadataResource(page), uri)
				.getTemplatedBaseLink();
	}

	private Link getShowsEpisodesLink(Page<EpisodeDTO> page, UriComponentsBuilder baseContentLink) {
		UriComponentsBuilder uri = baseContentLink.cloneBuilder()
				.pathSegment("shows")
				.pathSegment("{showId}")
				.pathSegment("episodes");
		return new PageLinksAssembler(new PageMetadataResource(page), uri)
				.getTemplatedBaseLink();
	}

	public List<EpisodeResource> getData() {
		return data;
	}

	public PageMetadataResource getPageMetadataResource() {
		return pageMetadataResource;
	}
}
