package com.sandjelkovic.dispatchd.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.LinkAssembler;
import com.sandjelkovic.dispatchd.api.RelNamesConstants;
import com.sandjelkovic.dispatchd.controllers.rest.ReportController;
import com.sandjelkovic.dispatchd.controllers.rest.ReportTemplateController;
import com.sandjelkovic.dispatchd.data.dto.ReportDTO;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ReportDTOResource extends ResourceSupport {

	@JsonProperty("_embedded")
	private ReportDTO data;

	public ReportDTOResource(ReportDTO reportDTO) {
		this.data = reportDTO;

		ControllerLinkBuilder reportContentsBase = ControllerLinkBuilder.linkTo(methodOn(ReportController.class).getReportContents(data.getId(), null));
		this.add(LinkAssembler.getTemplatedForPagingBaseLink(reportContentsBase).withRel(RelNamesConstants.REPORT_CONTENTS));

		this.add(ControllerLinkBuilder.linkTo(methodOn(ReportController.class).getReport(data.getId())).withSelfRel());
		this.add(LinkAssembler.getTemplatedForPagingBaseLink(linkTo(ReportController.class)).withRel(RelNamesConstants.USER_REPORTS));

		this.add(ControllerLinkBuilder.linkTo(methodOn(ReportTemplateController.class).getTemplate(data.getTemplateId())).withRel(RelNamesConstants.TEMPLATE));
	}

	public ReportDTO getData() {
		return data;
	}
}
