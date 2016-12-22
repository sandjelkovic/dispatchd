package com.sandjelkovic.dispatchd.controllers.rest;

import com.sandjelkovic.dispatchd.api.resources.EpisodeResource;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.data.dto.EpisodeDTO;
import com.sandjelkovic.dispatchd.data.dto.SeasonDTO;
import com.sandjelkovic.dispatchd.data.dto.TvShowDto;
import com.sandjelkovic.dispatchd.data.entities.Episode;
import com.sandjelkovic.dispatchd.exception.ResourceNotFoundException;
import com.sandjelkovic.dispatchd.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = Constants.REST_ENDPOINT_API_PREFIX + "/content",
		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE},
		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class ContentController {

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private EpisodeService episodeService;

	@RequestMapping(path = "/episodes/{episodeId}")
	public EpisodeResource getEpisode(@PathVariable Long episodeId) {
		Episode episode = episodeService.findOne(episodeId)
				.orElseThrow(ResourceNotFoundException::new);
		EpisodeDTO dto = conversionService.convert(episode, EpisodeDTO.class);
		return new EpisodeResource(dto);
	}

	@RequestMapping(path = "/episodes")
	public List<EpisodeDTO> getEpisodesOfTvShow(@PathVariable Long showId) {
		return new ArrayList<>();
	}

	@RequestMapping(path = "/seasons/{seasonId}/episodes")
	public List<EpisodeDTO> getEpisodesOfSeason(@PathVariable Long seasonId) {
		return new ArrayList<>();
	}

	@RequestMapping(path = "/shows/{showId}")
	public List<TvShowDto> getShow(@PathVariable Long showId) {
		return new ArrayList<>();
	}

	@RequestMapping(path = "/seasons/{seasonId}")
	public List<SeasonDTO> getSeason(@PathVariable Long seasonId) {
		return new ArrayList<>();
	}

}
