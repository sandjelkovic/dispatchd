package com.sandjelkovic.dispatchd.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.controller.ReportController;
import com.sandjelkovic.dispatchd.api.controller.ReportTemplateController;
import com.sandjelkovic.dispatchd.api.dto.EpisodeDTO;
import com.sandjelkovic.dispatchd.api.dto.ReportDTO;
import com.sandjelkovic.dispatchd.api.link.LinkAssembler;
import com.sandjelkovic.dispatchd.api.link.PageLinksAssembler;
import com.sandjelkovic.dispatchd.api.link.RelNamesConstants;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

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

		ControllerLinkBuilder reportContentsBase = linkTo(methodOn(ReportController.class).getReportContents(reportDTO.getId(), null));
		this.add(LinkAssembler.getPageableTemplatedBaseLink(reportContentsBase).withRel(Link.REL_SELF));
		this.add(linkTo(methodOn(ReportTemplateController.class).getTemplate(reportDTO.getId())).withRel(RelNamesConstants.REPORT));
		this.add(LinkAssembler.getPageableTemplatedBaseLink(linkTo(ReportController.class)).withRel(RelNamesConstants.USER_REPORTS));
		this.add(linkTo(methodOn(ReportTemplateController.class).getTemplate(reportDTO.getTemplateId())).withRel(RelNamesConstants.TEMPLATE));
	}

	public List<EpisodeResource> getData() {
		return data;
	}

	public PageMetadataResource getPageMetadataResource() {
		return pageMetadataResource;
	}
}