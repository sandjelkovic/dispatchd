package com.sandjelkovic.dispatchd.gateway.api.processor;

import com.sandjelkovic.dispatchd.gateway.api.controller.ContentController;
import com.sandjelkovic.dispatchd.gateway.api.link.PageLinksAssembler;
import com.sandjelkovic.dispatchd.gateway.api.link.RelNamesConstants;
import com.sandjelkovic.dispatchd.gateway.api.resource.EpisodeListResource;
import com.sandjelkovic.dispatchd.gateway.api.resource.PageMetadataResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author ${sandjelkovic}
 * @date 9.3.17.
 */
@Component
public class EpisodeListResourceProcessor implements ResourceProcessor<EpisodeListResource> {
	@Override
	public EpisodeListResource process(EpisodeListResource resource) {
		UriComponentsBuilder baseContentLink = linkTo(ContentController.class).toUriComponentsBuilder();
		PageMetadataResource pageMetadata = resource.getPageMetadataResource();

		resource.add(createShowsEpisodesLink(pageMetadata, baseContentLink).withRel(RelNamesConstants.SHOW_EPISODES));
		resource.add(createSeasonsEpisodesLink(pageMetadata, baseContentLink).withRel(RelNamesConstants.SEASON_EPISODES));

		return resource;
	}

	private Link createSeasonsEpisodesLink(PageMetadataResource pageMetadata, UriComponentsBuilder baseContentLink) {
		UriComponentsBuilder uri = baseContentLink.cloneBuilder()
				.pathSegment("seasons")
				.pathSegment("{seasonId}")
				.pathSegment("episodes");
		return new PageLinksAssembler(pageMetadata, uri)
				.getTemplatedBaseLink();
	}

	private Link createShowsEpisodesLink(PageMetadataResource pageMetadata, UriComponentsBuilder baseContentLink) {
		UriComponentsBuilder uri = baseContentLink.cloneBuilder()
				.pathSegment("shows")
				.pathSegment("{showId}")
				.pathSegment("episodes");
		return new PageLinksAssembler(pageMetadata, uri)
				.getTemplatedBaseLink();
	}
}
