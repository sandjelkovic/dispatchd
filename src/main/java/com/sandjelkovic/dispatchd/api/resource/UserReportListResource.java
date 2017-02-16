package com.sandjelkovic.dispatchd.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.controller.ReportController;
import com.sandjelkovic.dispatchd.api.controller.ReportTemplateController;
import com.sandjelkovic.dispatchd.api.dto.ReportDTO;
import com.sandjelkovic.dispatchd.api.link.LinkAssembler;
import com.sandjelkovic.dispatchd.api.link.PageLinksAssembler;
import com.sandjelkovic.dispatchd.api.link.RelNamesConstants;
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
