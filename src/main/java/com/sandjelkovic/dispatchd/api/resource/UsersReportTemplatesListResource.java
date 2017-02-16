package com.sandjelkovic.dispatchd.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.controller.ReportController;
import com.sandjelkovic.dispatchd.api.controller.ReportTemplateController;
import com.sandjelkovic.dispatchd.api.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.api.link.LinkAssembler;
import com.sandjelkovic.dispatchd.api.link.PageLinksAssembler;
import com.sandjelkovic.dispatchd.api.link.RelNamesConstants;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UsersReportTemplatesListResource extends PageableListResource {
	@JsonProperty("_embedded")
	private List<ReportTemplateResource> data;
	@JsonProperty("page")
	private PageMetadataResource pageMetadataResource;

	public UsersReportTemplatesListResource(Page<ReportTemplateDTO> page) {
		super(new PageLinksAssembler(new PageMetadataResource(page), linkTo(ReportTemplateController.class)));
		this.data = page.getContent().stream()
				.map(ReportTemplateResource::new)
				.collect(toList());
		this.pageMetadataResource = new PageMetadataResource(page);

		this.add(LinkAssembler.getPageableTemplatedBaseLink(linkTo(ReportTemplateController.class)).withSelfRel());
		this.add(LinkAssembler.getPageableTemplatedBaseLink(linkTo(ReportController.class)).withRel(RelNamesConstants.USER_REPORTS));
	}

	public List<ReportTemplateResource> getData() {
		return data;
	}

	public PageMetadataResource getPageMetadataResource() {
		return pageMetadataResource;
	}
}
