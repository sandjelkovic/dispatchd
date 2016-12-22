package com.sandjelkovic.dispatchd.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.LinkAssembler;
import com.sandjelkovic.dispatchd.api.RelNamesConstants;
import com.sandjelkovic.dispatchd.controllers.rest.ReportController;
import com.sandjelkovic.dispatchd.controllers.rest.ReportTemplateController;
import com.sandjelkovic.dispatchd.data.dto.ReportTemplateDTO;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ReportTemplateDTOResource extends ResourceSupport {

	@JsonProperty("_embedded")
	private ReportTemplateDTO data;

	public ReportTemplateDTOResource(ReportTemplateDTO templateDto) {
		this.data = templateDto;
		this.add(linkTo(methodOn(ReportTemplateController.class).getTemplate(data.getId())).withSelfRel());
		this.add(LinkAssembler.getTemplatedForPagingBaseLink(linkTo(ReportTemplateController.class)).withRel(RelNamesConstants.USER_REPORT_TEMPLATES));
		this.add(linkTo(ReportController.class).withRel("reports")); // todo Implement relation between report <=> report template and make a query from here
	}

	public ReportTemplateDTO getData() {
		return data;
	}

	public void setData(ReportTemplateDTO data) {
		this.data = data;
	}
}
