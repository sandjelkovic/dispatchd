package com.sandjelkovic.dispatchd.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.LinkAssembler;
import com.sandjelkovic.dispatchd.api.RelNamesConstants;
import com.sandjelkovic.dispatchd.controllers.rest.ContentController;
import com.sandjelkovic.dispatchd.data.dto.EpisodeDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class EpisodeResource extends ResourceSupport {

	@JsonProperty("_embedded")
	private EpisodeDTO data;

	public EpisodeResource(EpisodeDTO episodeDTO) {
		this.data = episodeDTO;

		this.add(ControllerLinkBuilder.linkTo(methodOn(ContentController.class).getEpisode(data.getId())).withSelfRel());

		ControllerLinkBuilder episodesOfShowLink = linkTo(methodOn(ContentController.class).getEpisodesOfTvShow(episodeDTO.getTvShowId()));
		this.add(LinkAssembler.getTemplatedForPagingBaseLink(episodesOfShowLink).withRel(RelNamesConstants.SHOW_EPISODES));

		ControllerLinkBuilder episodesOfSeasonLink = linkTo(methodOn(ContentController.class).getEpisodesOfSeason(episodeDTO.getTvShowId()));
		this.add(LinkAssembler.getTemplatedForPagingBaseLink(episodesOfSeasonLink).withRel(RelNamesConstants.SEASON_EPISODES));

		this.add(linkTo(methodOn(ContentController.class).getSeason(data.getSeasonId())).withRel(RelNamesConstants.SEASON));
		this.add(linkTo((methodOn(ContentController.class).getShow(data.getTvShowId()))).withRel(RelNamesConstants.SHOW));
	}

	public EpisodeDTO getData() {
		return data;
	}
}
