package com.sandjelkovic.dispatchd.api.controller;

import com.sandjelkovic.dispatchd.api.dto.ImportStatusDto;
import com.sandjelkovic.dispatchd.api.dto.MediaUrlDto;
import com.sandjelkovic.dispatchd.api.resource.ImportStatusResource;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.domain.facade.ImporterFacade;
import com.sandjelkovic.dispatchd.exception.InvalidMediaImportUrlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ImportRestController {

	private static final Logger log = LoggerFactory.getLogger(ImportRestController.class);

	@Autowired
	private ImporterFacade importerFacade;

	@RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ImportStatusResource doImport(@RequestBody @Valid MediaUrlDto mediaUrlDto) {
		UriComponents uriComponents = getUriComponentsFromMediaUrl(mediaUrlDto.getMediaUrl());
		ImportStatusDto status = getImportFacadeFromUri(uriComponents).importFromUriComponents(uriComponents);
		return new ImportStatusResource(status);
	}

	@RequestMapping(value = "/{id}", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public ImportStatusResource getImportStatus(@PathVariable Long id) {
		return new ImportStatusResource(importerFacade.getImportStatus(id));
	}

	private UriComponents getUriComponentsFromMediaUrl(String mediaUrlToImport) {
		UriComponents uriComponents = null;
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
