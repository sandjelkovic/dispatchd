package com.sandjelkovic.dispatchd.api.controller;

import com.sandjelkovic.dispatchd.api.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.api.dto.ShowConnectionsDto;
import com.sandjelkovic.dispatchd.api.resource.ReportTemplateResource;
import com.sandjelkovic.dispatchd.api.resource.UserReportTemplateListResource;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.converter.ReportTemplate2DTOConverter;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate;
import com.sandjelkovic.dispatchd.domain.data.entity.User;
import com.sandjelkovic.dispatchd.domain.facade.ReportFacade;
import com.sandjelkovic.dispatchd.domain.service.UserService;
import com.sandjelkovic.dispatchd.exception.EditTemplateAccessDeniedException;
import com.sandjelkovic.dispatchd.exception.ReportTemplateNotFoundException;
import com.sandjelkovic.dispatchd.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@Slf4j
public class ReportTemplateController extends BaseController {

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private ReportTemplate2DTOConverter template2DTOConverter;

	@Autowired
	private ReportFacade reportFacade;

	@Autowired
	private UserService userService;

	@RequestMapping(method = GET)
	public UserReportTemplateListResource getTemplates(Pageable pageable, Principal user) {
		Page<ReportTemplateDTO> convertedPage = reportFacade.findTemplatesForUser(pageable, user.getName())
				.map(template2DTOConverter);
		UserReportTemplateListResource userReportTemplateListResource = new UserReportTemplateListResource(convertedPage);
		return resourceProcessorInvoker.invokeProcessorsFor(userReportTemplateListResource);
	}

	@RequestMapping(value = "/{templateId}", method = GET)
	public ReportTemplateResource getTemplate(@PathVariable Long templateId) {
		Optional<ReportTemplate> template = reportFacade.findTemplate(templateId);
		if (!template.isPresent()) {
			throw new ReportTemplateNotFoundException("Report template with id:[" + templateId + "] doesn't exist for this user");
		}
		ReportTemplateDTO returnTemplate = conversionService.convert(template.get(), ReportTemplateDTO.class);
		ReportTemplateResource reportTemplateResource = new ReportTemplateResource(returnTemplate);
		return resourceProcessorInvoker.invokeProcessorsFor(reportTemplateResource);
	}

	@RequestMapping(method = POST)
	@ResponseStatus(CREATED)
	public ReportTemplateResource createTemplate(@Valid @RequestBody ReportTemplateDTO templateDTO, Principal user) {
		ReportTemplate template = conversionService.convert(templateDTO, ReportTemplate.class);
		template.setUser(userService.findByUsername(user.getName())
				.orElseThrow(UserNotFoundException::new));
		ReportTemplate resultTemplate = reportFacade.save(template);

		ReportTemplateDTO returnTemplate = conversionService.convert(resultTemplate, ReportTemplateDTO.class);

		ReportTemplateResource reportTemplateResource = new ReportTemplateResource(returnTemplate);
		return resourceProcessorInvoker.invokeProcessorsFor(reportTemplateResource);
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
		ReportTemplate resultTemplate = reportFacade.save(template);

		ReportTemplateDTO returnTemplate = conversionService.convert(resultTemplate, ReportTemplateDTO.class);

		ReportTemplateResource reportTemplateResource = new ReportTemplateResource(returnTemplate);
		return resourceProcessorInvoker.invokeProcessorsFor(reportTemplateResource);
	}

	@RequestMapping(value = "/{templateId}", method = DELETE)
	@ResponseStatus(OK)
	public void deleteTemplate(@PathVariable Long templateId) {
		reportFacade.deleteTemplate(templateId);
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
		reportFacade.findTemplate(templateId)
				.map(ReportTemplate::getUser)
				.map(User::getUsername)
				.filter(e -> StringUtils.equals(e, principal.getName()))
				.orElseThrow(EditTemplateAccessDeniedException::new);
	}

}
