package com.sandjelkovic.dispatchd.api.controller;

import com.sandjelkovic.dispatchd.api.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.api.dto.ShowConnectionsDto;
import com.sandjelkovic.dispatchd.api.resource.ReportTemplateResource;
import com.sandjelkovic.dispatchd.api.resource.UsersReportTemplatesListResource;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.converter.ReportTemplate2DTOConverter;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate;
import com.sandjelkovic.dispatchd.domain.data.entity.User;
import com.sandjelkovic.dispatchd.domain.service.ReportService;
import com.sandjelkovic.dispatchd.domain.service.UserService;
import com.sandjelkovic.dispatchd.exception.EditTemplateAccessDeniedException;
import com.sandjelkovic.dispatchd.exception.ReportTemplateNotFoundException;
import com.sandjelkovic.dispatchd.exception.UserNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(value = Constants.REST_ENDPOINT_API_PREFIX + "/templates",
		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE},
		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
@ExposesResourceFor(ReportTemplateDTO.class)
public class ReportTemplateController {
	private static final Logger log = LoggerFactory.getLogger(ReportTemplateController.class);

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private ReportTemplate2DTOConverter template2DTOConverter;

	@Autowired
	private ReportService reportService;

	@Autowired
	private UserService userService;

	@RequestMapping(method = GET)
	public UsersReportTemplatesListResource getTemplates(Pageable pageable, Principal user) {
		Page<ReportTemplateDTO> convertedPage = reportService.findTemplatesForUser(pageable, user.getName())
				.map(template2DTOConverter);
		return new UsersReportTemplatesListResource(convertedPage);
	}

	@RequestMapping(value = "/{templateId}", method = GET)
	public ReportTemplateResource getTemplate(@PathVariable Long templateId) {
		Optional<ReportTemplate> template = reportService.findTemplate(templateId);
		if (!template.isPresent()) {
			throw new ReportTemplateNotFoundException("Report template with id:[" + templateId + "] doesn't exist for this user");
		}
		ReportTemplateDTO returnTemplate = conversionService.convert(template.get(), ReportTemplateDTO.class);
		return new ReportTemplateResource(returnTemplate);
	}

	@RequestMapping(method = POST)
	@ResponseStatus(CREATED)
	public ReportTemplateResource createTemplate(@Valid @RequestBody ReportTemplateDTO templateDTO, Principal user) {
		ReportTemplate template = conversionService.convert(templateDTO, ReportTemplate.class);
		template.setUser(userService.findByUsername(user.getName())
				.orElseThrow(UserNotFoundException::new));
		ReportTemplate resultTemplate = reportService.save(template);

		ReportTemplateDTO returnTemplate = conversionService.convert(resultTemplate, ReportTemplateDTO.class);

		return new ReportTemplateResource(returnTemplate);
	}

	@RequestMapping(value = "/{templateId}", method = PUT)
	@ResponseStatus(OK)
	public ReportTemplateResource editTemplate(@PathVariable Long templateId,
	                                           @Valid @RequestBody ReportTemplateDTO templateDTO,
	                                           Principal principal) {
		templateDTO.id(templateId);

		ReportTemplate template = conversionService.convert(templateDTO, ReportTemplate.class);
		isTemplateIsOwnedByUser(templateId, principal);

		template.setUser(userService.findByUsername(principal.getName())
				.orElseThrow(UserNotFoundException::new));
		ReportTemplate resultTemplate = reportService.save(template);

		ReportTemplateDTO returnTemplate = conversionService.convert(resultTemplate, ReportTemplateDTO.class);

		return new ReportTemplateResource(returnTemplate);
	}

	@RequestMapping(value = "/{templateId}", method = DELETE)
	@ResponseStatus(OK)
	public void deleteTemplate(@PathVariable Long templateId) {
		reportService.deleteTemplate(templateId);
	}

	@RequestMapping(value = "/{templateId}/shows", method = POST)
	@ResponseStatus(OK)
	public ReportTemplateResource connectWithShows(@PathVariable Long templateId, @RequestBody ShowConnectionsDto showConnectionsDto) {
		// override all relations template <> shows (delete + update)
		return null;
	}

	@RequestMapping(value = "/{templateId}/shows", method = PUT)
	@ResponseStatus(OK)
	public ReportTemplateResource addConnectionsToShow(@PathVariable Long templateId, @RequestBody ShowConnectionsDto showConnectionsDto) {
		// update relations template <> shows. Update order if already present
		return null;
	}

	@RequestMapping(value = "/{templateId}/shows", method = DELETE)
	@ResponseStatus(OK)
	public ReportTemplateResource deleteConnectionsToShow(@PathVariable Long templateId, @RequestBody ShowConnectionsDto showConnectionsDto) {
		// delete relations template <> shows. Update order if present
		return null;
	}

	@RequestMapping(value = "/{templateId}/shows", method = GET)
	@ResponseStatus(OK)
	public ReportTemplateResource getConnectionsToShow(@PathVariable Long templateId, @RequestBody ShowConnectionsDto showConnectionsDto) {
		// retrieve all shows connected to this template
		return null;
	}

	private void isTemplateIsOwnedByUser(Long templateId, Principal principal) {
		reportService.findTemplate(templateId)
				.map(ReportTemplate::getUser)
				.map(User::getUsername)
				.filter(e -> StringUtils.equals(e, principal.getName()))
				.orElseThrow(EditTemplateAccessDeniedException::new);
	}

}