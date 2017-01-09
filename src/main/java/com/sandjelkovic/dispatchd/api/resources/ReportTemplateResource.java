package com.sandjelkovic.dispatchd.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.LinkAssembler;
import com.sandjelkovic.dispatchd.api.RelNamesConstants;
import com.sandjelkovic.dispatchd.controllers.rest.ReportController;
import com.sandjelkovic.dispatchd.controllers.rest.ReportTemplateController;
import com.sandjelkovic.dispatchd.data.dto.ReportTemplateDTO;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ReportTemplateResource extends ResourceSupport {

	@JsonProperty("_embedded")
	private ReportTemplateDTO data;

	public ReportTemplateResource(ReportTemplateDTO templateDto) {
		this.data = templateDto;
		this.add(linkTo(methodOn(ReportTemplateController.class).getTemplate(data.getId())).withSelfRel());
		this.add(LinkAssembler.getPageableTemplatedBaseLink(linkTo(ReportTemplateController.class)).withRel(RelNamesConstants.USER_REPORT_TEMPLATES));
		this.add(linkTo(ReportController.class).withRel(RelNamesConstants.USER_REPORTS));
		String hrefToReportTemplate = LinkAssembler.hrefFromComponentBuilder(linkTo(ReportController.class).toUriComponentsBuilder()
				.queryParam("templateId", templateDto.getId()));
		this.add(new Link(hrefToReportTemplate, RelNamesConstants.REPORTS_OF_TEMPLATE));
	}

	public ReportTemplateDTO getData() {
		return data;
	}

	public void setData(ReportTemplateDTO data) {
		this.data = data;
	}
}