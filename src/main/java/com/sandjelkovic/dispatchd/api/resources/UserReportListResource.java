package com.sandjelkovic.dispatchd.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.LinkAssembler;
import com.sandjelkovic.dispatchd.api.PageLinksAssembler;
import com.sandjelkovic.dispatchd.api.RelNamesConstants;
import com.sandjelkovic.dispatchd.controllers.rest.ReportController;
import com.sandjelkovic.dispatchd.controllers.rest.ReportTemplateController;
import com.sandjelkovic.dispatchd.data.dto.ReportDTO;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserReportListResource extends PageableListResource {
	@JsonProperty("_embedded")
	private List<ReportResource> data;
	@JsonProperty("page")
	private PageMetadataResource pageMetadataResource;

	public UserReportListResource(Page<ReportDTO> page) {
		super(new PageLinksAssembler(new PageMetadataResource(page), linkTo(ReportController.class)));
		this.data = page.getContent().stream()
				.map(ReportResource::new)
				.collect(toList());
		this.pageMetadataResource = new PageMetadataResource(page);

		this.add(LinkAssembler.getPageableTemplatedBaseLink(linkTo(ReportController.class)).withSelfRel());
		this.add(LinkAssembler.getPageableTemplatedBaseLink(linkTo(ReportController.class)).withRel(RelNamesConstants.USER_REPORTS));
		this.add(linkTo(ReportTemplateController.class).withRel(RelNamesConstants.USER_REPORT_TEMPLATES));
	}

	public List<ReportResource> getData() {
		return data;
	}

	public PageMetadataResource getPageMetadataResource() {
		return pageMetadataResource;
	}
}
