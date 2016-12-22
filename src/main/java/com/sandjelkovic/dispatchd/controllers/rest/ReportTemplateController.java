package com.sandjelkovic.dispatchd.controllers.rest;

import com.sandjelkovic.dispatchd.api.resources.ReportTemplateDTOResource;
import com.sandjelkovic.dispatchd.api.resources.UsersReportTemplatesDTOListResource;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.converter.ReportTemplate2DTOConverter;
import com.sandjelkovic.dispatchd.data.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;
import com.sandjelkovic.dispatchd.data.entities.User;
import com.sandjelkovic.dispatchd.exception.EditTemplateAccessDeniedException;
import com.sandjelkovic.dispatchd.exception.ReportTemplateNotFoundException;
import com.sandjelkovic.dispatchd.exception.UserNotFoundException;
import com.sandjelkovic.dispatchd.service.ReportService;
import com.sandjelkovic.dispatchd.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;

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
	public UsersReportTemplatesDTOListResource getTemplates(Pageable pageable, Principal user) {
		Page<ReportTemplateDTO> convertedPage = reportService.findAllForCurrentUser(pageable, user.getName())
				.map(template2DTOConverter);
		return new UsersReportTemplatesDTOListResource(convertedPage);
	}

	@RequestMapping(value = "/{templateId}", method = GET)
	public ReportTemplateDTOResource getTemplate(@PathVariable Long templateId) {
		Optional<ReportTemplate> template = reportService.findTemplate(templateId);
		if (!template.isPresent()) {
			throw new ReportTemplateNotFoundException("Report template with id:[" + templateId + "] doesn't exist for this user");
		}
		ReportTemplateDTO returnTemplate = conversionService.convert(template.get(), ReportTemplateDTO.class);
		return new ReportTemplateDTOResource(returnTemplate);
	}

	@RequestMapping(method = POST)
	@ResponseStatus(CREATED)
	public ReportTemplateDTOResource createTemplate(@Valid @RequestBody ReportTemplateDTO templateDTO, Principal user) {
		ReportTemplate template = conversionService.convert(templateDTO, ReportTemplate.class);
		template.setUser(userService.findByUsername(user.getName())
				.orElseThrow(UserNotFoundException::new));
		ReportTemplate resultTemplate = reportService.save(template);

		ReportTemplateDTO returnTemplate = conversionService.convert(resultTemplate, ReportTemplateDTO.class);

		return new ReportTemplateDTOResource(returnTemplate);
	}

	@RequestMapping(value = "/{templateId}", method = PUT)
	@ResponseStatus(OK)
	public ReportTemplateDTOResource editTemplate(@PathVariable Long templateId,
	                                              @Valid @RequestBody ReportTemplateDTO templateDTO,
	                                              Principal principal) {
		templateDTO.id(templateId);

		ReportTemplate template = conversionService.convert(templateDTO, ReportTemplate.class);
		checkIfTemplateIsOwnedByUser(templateId, principal);

		template.setUser(userService.findByUsername(principal.getName())
				.orElseThrow(UserNotFoundException::new));
		ReportTemplate resultTemplate = reportService.save(template);

		ReportTemplateDTO returnTemplate = conversionService.convert(resultTemplate, ReportTemplateDTO.class);

		return new ReportTemplateDTOResource(returnTemplate);
	}

	private void checkIfTemplateIsOwnedByUser(Long templateId, Principal principal) {
		reportService.findTemplate(templateId)
				.map(ReportTemplate::getUser)
				.map(User::getUsername)
				.filter(e -> StringUtils.equals(e, principal.getName()))
				.orElseThrow(EditTemplateAccessDeniedException::new);
	}

}
