package com.sandjelkovic.dispatchd.api.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.PageLinksAssembler;
import com.sandjelkovic.dispatchd.api.RelNamesConstants;
import com.sandjelkovic.dispatchd.controllers.rest.ReportTemplateController;
import com.sandjelkovic.dispatchd.data.dto.ReportTemplateDTO;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class UsersReportTemplatesDTOListResource extends ResourceSupport {
	@JsonProperty("_embedded")
	private List<ReportTemplateDTOResource> data;
	@JsonProperty("page")
	private PageMetadataResource pageMetadataResource;

	public UsersReportTemplatesDTOListResource(Page<ReportTemplateDTO> page) {
		this.data = page.getContent().stream()
				.map(ReportTemplateDTOResource::new)
				.collect(toList());
		this.pageMetadataResource = new PageMetadataResource(page);
		PageLinksAssembler pageLinksAssembler = new PageLinksAssembler(pageMetadataResource, linkTo(ReportTemplateController.class));

		this.add(pageLinksAssembler.getTemplatedBaseLink().withRel(RelNamesConstants.USER_REPORT_TEMPLATES));
		this.add(Stream.of(pageLinksAssembler.getFirstLink(), pageLinksAssembler.getPreviousLink(),
				pageLinksAssembler.getCurrentLink(), pageLinksAssembler.getNextLink(), pageLinksAssembler.getLastLink())
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(toList()));
	}

	public List<ReportTemplateDTOResource> getData() {
		return data;
	}

	public PageMetadataResource getPageMetadataResource() {
		return pageMetadataResource;
	}
}
