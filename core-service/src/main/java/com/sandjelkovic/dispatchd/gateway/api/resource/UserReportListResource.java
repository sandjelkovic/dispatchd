package com.sandjelkovic.dispatchd.gateway.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.gateway.api.controller.ReportController;
import com.sandjelkovic.dispatchd.gateway.api.dto.ReportDTO;
import com.sandjelkovic.dispatchd.gateway.api.link.PageLinksAssembler;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
@EqualsAndHashCode(callSuper = true)
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
