package com.sandjelkovic.dispatchd.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.controller.ReportController;
import com.sandjelkovic.dispatchd.api.dto.ReportDTO;
import com.sandjelkovic.dispatchd.api.link.PageLinksAssembler;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
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
	}
}
