package com.sandjelkovic.dispatchd.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.PageLinksAssembler;
import com.sandjelkovic.dispatchd.controllers.rest.ReportController;
import com.sandjelkovic.dispatchd.data.dto.ReportDTO;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UserReportListResource extends PageableListResource {
	@JsonProperty("_embedded")
	private List<ReportDTOResource> data;
	@JsonProperty("page")
	private PageMetadataResource pageMetadataResource;

	public UserReportListResource(Page<ReportDTO> page) {
		super(new PageLinksAssembler(new PageMetadataResource(page), linkTo(ReportController.class)));
		this.data = page.getContent().stream()
				.map(ReportDTOResource::new)
				.collect(toList());
		this.pageMetadataResource = new PageMetadataResource(page);
	}

	public List<ReportDTOResource> getData() {
		return data;
	}

	public PageMetadataResource getPageMetadataResource() {
		return pageMetadataResource;
	}
}
