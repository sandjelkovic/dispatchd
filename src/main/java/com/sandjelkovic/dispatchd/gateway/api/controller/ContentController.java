package com.sandjelkovic.dispatchd.gateway.api.controller;

import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.domain.data.entity.Episode;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.service.ContentService;
import com.sandjelkovic.dispatchd.gateway.api.dto.EpisodeDTO;
import com.sandjelkovic.dispatchd.gateway.api.dto.SeasonDTO;
import com.sandjelkovic.dispatchd.gateway.api.dto.TvShowDTO;
import com.sandjelkovic.dispatchd.gateway.api.resource.EpisodeListResource;
import com.sandjelkovic.dispatchd.gateway.api.resource.EpisodeResource;
import com.sandjelkovic.dispatchd.gateway.api.resource.TvShowResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = Constants.REST_ENDPOINT_API_PREFIX + "/content",
		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE},
		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class ContentController extends BaseController {

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private ContentService contentService;

	@RequestMapping(path = "/episodes/{episodeId}")
	public EpisodeResource getEpisode(@PathVariable Long episodeId) {
		Episode episode = contentService.findEpisodeById(episodeId);
		EpisodeDTO dto = conversionService.convert(episode, EpisodeDTO.class);
		EpisodeResource episodeResource = new EpisodeResource(dto);
		return resourceProcessorInvoker.invokeProcessorsFor(episodeResource);
	}

	@RequestMapping(path = "/shows/{showId}/episodes")
	public EpisodeListResource getEpisodesOfTvShow(@PathVariable Long showId, Pageable pageable) {
		Page<EpisodeDTO> result = contentService.findEpisodeListByShow(showId, pageable)
				.map(source -> conversionService.convert(source, EpisodeDTO.class));

		UriComponentsBuilder uri = linkTo(ContentController.class).toUriComponentsBuilder()
				.pathSegment("shows")
				.pathSegment("{showId}")
				.pathSegment("episodes");

		EpisodeListResource episodeListResource = new EpisodeListResource(result, uri);
		return resourceProcessorInvoker.invokeProcessorsFor(episodeListResource);
	}

	@RequestMapping(path = "/seasons/{seasonId}/episodes")
	public EpisodeListResource getEpisodesOfSeason(@PathVariable Long seasonId, Pageable pageable) {
		Page<EpisodeDTO> result = contentService.findEpisodeListBySeason(seasonId, pageable)
				.map(source -> conversionService.convert(source, EpisodeDTO.class));

		UriComponentsBuilder uri = linkTo(ContentController.class).toUriComponentsBuilder()
				.pathSegment("seasons")
				.pathSegment("{seasonsId}")
				.pathSegment("episodes");

		EpisodeListResource episodeListResource = new EpisodeListResource(result, uri);
		return resourceProcessorInvoker.invokeProcessorsFor(episodeListResource);
	}

	@RequestMapping(path = "/shows/{showId}")
	public TvShowResource getShow(@PathVariable Long showId) {
		TvShow show = contentService.findShow(showId);
		TvShowResource tvShowResource = new TvShowResource(conversionService.convert(show, TvShowDTO.class));
		return resourceProcessorInvoker.invokeProcessorsFor(tvShowResource);
	}

	@RequestMapping(path = "/seasons/{seasonId}")
	public List<SeasonDTO> getSeason(@PathVariable Long seasonId) {
		return new ArrayList<>();
	}

	// intentionally no /seasons or /episodes endpoint. Bind it to something.
}
