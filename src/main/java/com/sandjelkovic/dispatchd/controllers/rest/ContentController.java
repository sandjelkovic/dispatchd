package com.sandjelkovic.dispatchd.controllers.rest;

import com.sandjelkovic.dispatchd.api.PageLinksAssembler;
import com.sandjelkovic.dispatchd.api.processor.PageableResourceLinksProcessor;
import com.sandjelkovic.dispatchd.api.resources.EpisodeListResource;
import com.sandjelkovic.dispatchd.api.resources.EpisodeResource;
import com.sandjelkovic.dispatchd.api.resources.PageMetadataResource;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.data.dto.EpisodeDTO;
import com.sandjelkovic.dispatchd.data.dto.SeasonDTO;
import com.sandjelkovic.dispatchd.data.dto.TvShowDto;
import com.sandjelkovic.dispatchd.data.entities.Episode;
import com.sandjelkovic.dispatchd.service.ContentService;
import com.sandjelkovic.dispatchd.service.EpisodeService;
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
public class ContentController {

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private EpisodeService episodeService;

	@Autowired
	private ContentService contentService;

	@RequestMapping(path = "/episodes/{episodeId}")
	public EpisodeResource getEpisode(@PathVariable Long episodeId) {
		Episode episode = contentService.findEpisodeById(episodeId);
		EpisodeDTO dto = conversionService.convert(episode, EpisodeDTO.class);
		return new EpisodeResource(dto);
	}

	@RequestMapping(path = "/shows/{showId}/episodes")
	public EpisodeListResource getEpisodesOfTvShow(@PathVariable Long showId, Pageable pageable) {
		Page<EpisodeDTO> result = contentService.findEpisodeListByShow(showId, pageable)
				.map(source -> conversionService.convert(source, EpisodeDTO.class));

		EpisodeListResource episodeListResource = new EpisodeListResource(result);

		UriComponentsBuilder uri = linkTo(ContentController.class).toUriComponentsBuilder()
				.pathSegment("shows")
				.pathSegment("{showId}")
				.pathSegment("episodes");
		PageLinksAssembler pageLinksAssembler = new PageLinksAssembler(new PageMetadataResource(result), uri);
		PageableResourceLinksProcessor.process(episodeListResource, pageLinksAssembler);

		return episodeListResource;
	}

	@RequestMapping(path = "/seasons/{seasonId}/episodes")
	public EpisodeListResource getEpisodesOfSeason(@PathVariable Long seasonId, Pageable pageable) {
		Page<EpisodeDTO> result = contentService.findEpisodeListBySeason(seasonId, pageable)
				.map(source -> conversionService.convert(source, EpisodeDTO.class));
		EpisodeListResource episodeListResource = new EpisodeListResource(result);

		UriComponentsBuilder uri = linkTo(ContentController.class).toUriComponentsBuilder()
				.pathSegment("seasons")
				.pathSegment("{seasonsId}")
				.pathSegment("episodes");
		PageLinksAssembler pageLinksAssembler = new PageLinksAssembler(new PageMetadataResource(result), uri);
		PageableResourceLinksProcessor.process(episodeListResource, pageLinksAssembler);

		return episodeListResource;
	}

	@RequestMapping(path = "/shows/{showId}")
	public List<TvShowDto> getShow(@PathVariable Long showId) {
		return new ArrayList<>();
	}

	@RequestMapping(path = "/seasons/{seasonId}")
	public List<SeasonDTO> getSeason(@PathVariable Long seasonId) {
		return new ArrayList<>();
	}

	// intentionally no /seasons or /episodes endpoint. Bind it to something.
}
