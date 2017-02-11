package com.sandjelkovic.dispatchd.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.LinkAssembler;
import com.sandjelkovic.dispatchd.api.RelNamesConstants;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.controllers.rest.ContentController;
import com.sandjelkovic.dispatchd.data.dto.EpisodeDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class EpisodeResource extends ResourceSupport {

	@JsonProperty("_embedded")
	private EpisodeDTO data;

	public EpisodeResource(EpisodeDTO episodeDTO) {
		this.data = episodeDTO;
		PageRequest defaultPage = new PageRequest(0, Constants.DEFAULT_PAGE_SIZE);

		this.add(ControllerLinkBuilder.linkTo(methodOn(ContentController.class).getEpisode(data.getId())).withSelfRel());

		ControllerLinkBuilder episodesOfShowLink = linkTo(methodOn(ContentController.class).getEpisodesOfTvShow(episodeDTO.getTvShowId(), defaultPage));
		this.add(LinkAssembler.getPageableTemplatedBaseLink(episodesOfShowLink).withRel(RelNamesConstants.SHOW_EPISODES));

		ControllerLinkBuilder episodesOfSeasonLink = linkTo(methodOn(ContentController.class).getEpisodesOfSeason(episodeDTO.getTvShowId(), defaultPage));
		this.add(LinkAssembler.getPageableTemplatedBaseLink(episodesOfSeasonLink).withRel(RelNamesConstants.SEASON_EPISODES));

		this.add(linkTo(methodOn(ContentController.class).getSeason(data.getSeasonId())).withRel(RelNamesConstants.SEASON));
		this.add(linkTo((methodOn(ContentController.class).getShow(data.getTvShowId()))).withRel(RelNamesConstants.SHOW));
	}

	public EpisodeDTO getData() {
		return data;
	}
}
