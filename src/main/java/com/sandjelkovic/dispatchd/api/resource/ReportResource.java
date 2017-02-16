package com.sandjelkovic.dispatchd.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.controller.ReportController;
import com.sandjelkovic.dispatchd.api.controller.ReportTemplateController;
import com.sandjelkovic.dispatchd.api.dto.ReportDTO;
import com.sandjelkovic.dispatchd.api.link.LinkAssembler;
import com.sandjelkovic.dispatchd.api.link.RelNamesConstants;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ReportResource extends ResourceSupport {

	@JsonProperty("_embedded")
	private ReportDTO data;

	public ReportResource(ReportDTO reportDTO) {
		this.data = reportDTO;

		ControllerLinkBuilder reportContentsBase = ControllerLinkBuilder.linkTo(methodOn(ReportController.class).getReportContents(data.getId(), null));
		this.add(LinkAssembler.getPageableTemplatedBaseLink(reportContentsBase).withRel(RelNamesConstants.REPORT_CONTENTS));

		this.add(ControllerLinkBuilder.linkTo(methodOn(ReportController.class).getReport(data.getId())).withSelfRel());
		this.add(LinkAssembler.getPageableTemplatedBaseLink(linkTo(ReportController.class)).withRel(RelNamesConstants.USER_REPORTS));

		this.add(ControllerLinkBuilder.linkTo(methodOn(ReportTemplateController.class).getTemplate(data.getTemplateId())).withRel(RelNamesConstants.TEMPLATE));
	}

	public ReportDTO getData() {
		return data;
	}
}
