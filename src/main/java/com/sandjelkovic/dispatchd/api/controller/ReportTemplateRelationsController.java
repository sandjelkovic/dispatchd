package com.sandjelkovic.dispatchd.api.controller;

import com.sandjelkovic.dispatchd.api.dto.RelationDTO;
import com.sandjelkovic.dispatchd.api.dto.ShowConnectionsDTO;
import com.sandjelkovic.dispatchd.api.dto.TvShowDTO;
import com.sandjelkovic.dispatchd.api.resource.TvShowListResource;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.converter.ReportTemplate2DTOConverter;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.facade.ReportFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * @author sandjelkovic
 * @date 23.3.17.
 */
@RestController
@RequestMapping(value = Constants.REST_ENDPOINT_API_PREFIX + "/templates",
		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE},
		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
@Slf4j
public class ReportTemplateRelationsController {
	@Autowired
	private ConversionService conversionService;

	@Autowired
	private ReportTemplate2DTOConverter template2DTOConverter;

	@Autowired
	private ReportFacade reportFacade;

	@RequestMapping(value = "/{templateId}", method = DELETE)
	@ResponseStatus(OK)
	public void deleteTemplate(@PathVariable Long templateId) {
		reportFacade.deleteTemplate(templateId);
	}

	@RequestMapping(value = "/{templateId}/shows", method = POST)
	@ResponseStatus(OK)
	public void connectWithShows(@PathVariable Long templateId, @RequestBody ShowConnectionsDTO showConnectionsDTO) {
		// create new relations (update if existing or throw error?)
	}

	@RequestMapping(value = "/{templateId}/shows", method = PUT)
	@ResponseStatus(OK)
	public void updateConnectionsToShow(@PathVariable Long templateId, @RequestBody ShowConnectionsDTO showConnectionsDTO) {
		// update  or create relations template <> shows. Update order if already present
	}

	@RequestMapping(value = "/{templateId}/shows/{showId}", method = PUT)
	@ResponseStatus(OK)
	public void updateConnectionToShow(@PathVariable Long templateId, @RequestBody RelationDTO relationDTO) {
		// update relation template <> show. Update order in collection
	}

	@RequestMapping(value = "/{templateId}/shows/{showId}", method = DELETE)
	@ResponseStatus(OK)
	public void deleteConnectionToShow(@PathVariable Long templateId, @PathVariable String showId) {
		// delete relations template <> shows.
		reportFacade.disconnectShow(templateId, showId);
	}

	@RequestMapping(value = "/{templateId}/shows", method = DELETE)
	@ResponseStatus(OK)
	public void deleteConnectionsToShow(@PathVariable Long templateId) {
		// delete relations template <> shows.
		reportFacade.disconnectAllShows(templateId);
	}

	@RequestMapping(value = "/{templateId}/shows", method = GET)
	@ResponseStatus(OK)
	public TvShowListResource getConnectionsToShow(@PathVariable Long templateId) {
		// retrieve all shows connected to this template
		List<TvShow> shows = reportFacade.findTemplateShows(templateId);
		List<TvShowDTO> showDtos = shows.stream()
				.map(show -> conversionService.convert(show, TvShowDTO.class))
				.collect(Collectors.toList());

		return null;
	}

}
