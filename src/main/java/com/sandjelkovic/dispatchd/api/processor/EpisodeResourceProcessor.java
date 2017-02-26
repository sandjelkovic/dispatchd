package com.sandjelkovic.dispatchd.api.processor;

import com.sandjelkovic.dispatchd.api.controller.ContentController;
import com.sandjelkovic.dispatchd.api.dto.EpisodeDTO;
import com.sandjelkovic.dispatchd.api.link.LinkAssembler;
import com.sandjelkovic.dispatchd.api.link.RelNamesConstants;
import com.sandjelkovic.dispatchd.api.resource.EpisodeResource;
import com.sandjelkovic.dispatchd.configuration.Constants;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author ${sandjelkovic}
 * @date 25.2.17.
 */
@Component
public class EpisodeResourceProcessor implements ResourceProcessor<EpisodeResource> {
	@Override
	public EpisodeResource process(EpisodeResource resource) {
		PageRequest defaultPage = new PageRequest(0, Constants.DEFAULT_PAGE_SIZE);
		EpisodeDTO content = resource.getData();

		resource.add(ControllerLinkBuilder.linkTo(methodOn(ContentController.class).getEpisode(content.getId())).withSelfRel());

		ControllerLinkBuilder episodesOfShowLink = linkTo(methodOn(ContentController.class).getEpisodesOfTvShow(content.getTvShowId(), defaultPage));
		resource.add(LinkAssembler.getPageableTemplatedBaseLink(episodesOfShowLink).withRel(RelNamesConstants.SHOW_EPISODES));

		ControllerLinkBuilder episodesOfSeasonLink = linkTo(methodOn(ContentController.class).getEpisodesOfSeason(content.getTvShowId(), defaultPage));
		resource.add(LinkAssembler.getPageableTemplatedBaseLink(episodesOfSeasonLink).withRel(RelNamesConstants.SEASON_EPISODES));

		resource.add(linkTo(methodOn(ContentController.class).getSeason(content.getSeasonId())).withRel(RelNamesConstants.SEASON));
		resource.add(linkTo((methodOn(ContentController.class).getShow(content.getTvShowId()))).withRel(RelNamesConstants.SHOW));
		return resource;
	}
}
