package com.sandjelkovic.dispatchd.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.PageLinksAssembler;
import com.sandjelkovic.dispatchd.controllers.rest.ReportController;
import com.sandjelkovic.dispatchd.data.dto.EpisodeDTO;
import com.sandjelkovic.dispatchd.data.dto.ReportDTO;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserReportContentResource extends PageableListResource {
	@JsonProperty("_embedded")
	private List<EpisodeResource> data;
	@JsonProperty("page")
	private PageMetadataResource pageMetadataResource;

	public UserReportContentResource(Page<EpisodeDTO> page, ReportDTO reportDTO) {
		super(new PageLinksAssembler(new PageMetadataResource(page),
				linkTo(methodOn(ReportController.class).getReportContents(reportDTO.getId(), null))));
		this.data = page.getContent().stream()
				.map(EpisodeResource::new)
				.collect(toList());
		this.pageMetadataResource = new PageMetadataResource(page);
	}

	public List<EpisodeResource> getData() {
		return data;
	}

	public PageMetadataResource getPageMetadataResource() {
		return pageMetadataResource;
	}
}
