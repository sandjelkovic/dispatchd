package com.sandjelkovic.dispatchd.api.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.controller.ReportController;
import com.sandjelkovic.dispatchd.api.dto.EpisodeDTO;
import com.sandjelkovic.dispatchd.api.dto.ReportDTO;
import com.sandjelkovic.dispatchd.api.link.PageLinksAssembler;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class UserReportContentResource extends PageableListResource {
	@JsonProperty("_embedded")
	private List<EpisodeResource> data;
	@JsonProperty("page")
	private PageMetadataResource pageMetadataResource;
	@JsonIgnore
	private ReportDTO reportDTO;

	public UserReportContentResource(Page<EpisodeDTO> page, ReportDTO reportDTO) {
		super(new PageLinksAssembler(new PageMetadataResource(page),
				linkTo(methodOn(ReportController.class).getReportContents(reportDTO.getId(), null))));
		this.reportDTO = reportDTO;
		this.data = page.getContent().stream()
				.map(EpisodeResource::new)
				.collect(toList());
		this.pageMetadataResource = new PageMetadataResource(page);
	}
}
