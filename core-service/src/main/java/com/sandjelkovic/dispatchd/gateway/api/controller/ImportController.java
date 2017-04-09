package com.sandjelkovic.dispatchd.gateway.api.controller;

import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.domain.facade.ImporterFacade;
import com.sandjelkovic.dispatchd.exception.InvalidMediaImportUrlException;
import com.sandjelkovic.dispatchd.gateway.api.dto.ImportStatusDTO;
import com.sandjelkovic.dispatchd.gateway.api.dto.MediaUrlDTO;
import com.sandjelkovic.dispatchd.gateway.api.resource.ImportStatusResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = Constants.REST_ENDPOINT_API_PREFIX + "/import",
		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE},
		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
@Slf4j
public class ImportController extends BaseController {

	@Autowired
	private ImporterFacade importerFacade;

	@RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ImportStatusResource doImport(@RequestBody @Valid MediaUrlDTO mediaUrlDTO) {
		UriComponents uriComponents = getUriComponentsFromMediaUrl(mediaUrlDTO.getMediaUrl());
		ImportStatusDTO status = getImportFacadeFromUri(uriComponents).importFromUriComponents(uriComponents);
		ImportStatusResource importStatusResource = new ImportStatusResource(status);
		return resourceProcessorInvoker.invokeProcessorsFor(importStatusResource);
	}

	@RequestMapping(value = "/{id}", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public ImportStatusResource getImportStatus(@PathVariable Long id) {
		ImportStatusResource importStatusResource = new ImportStatusResource(importerFacade.getImportStatus(id));
		return resourceProcessorInvoker.invokeProcessorsFor(importStatusResource);
	}

	private UriComponents getUriComponentsFromMediaUrl(String mediaUrlToImport) {
		UriComponents uriComponents;
		try {
			uriComponents = UriComponentsBuilder.fromHttpUrl(mediaUrlToImport).build().encode();
		} catch (IllegalArgumentException e) {
			throw new InvalidMediaImportUrlException();
		}
		return uriComponents;
	}

	private ImporterFacade getImportFacadeFromUri(UriComponents uriComponents) {
		// should decide on importer, for now use trakt importer and decide there
		return importerFacade;
	}
}
