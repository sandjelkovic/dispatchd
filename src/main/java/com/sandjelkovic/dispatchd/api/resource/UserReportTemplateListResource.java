package com.sandjelkovic.dispatchd.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.api.controller.ReportTemplateController;
import com.sandjelkovic.dispatchd.api.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.api.link.PageLinksAssembler;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter
@EqualsAndHashCode(callSuper = true)
public class UserReportTemplateListResource extends PageableListResource {
	@JsonProperty("_embedded")
	private List<ReportTemplateResource> data;
	@JsonProperty("page")
	private PageMetadataResource pageMetadataResource;

	public UserReportTemplateListResource(Page<ReportTemplateDTO> page) {
		super(new PageLinksAssembler(new PageMetadataResource(page), linkTo(ReportTemplateController.class)));
		this.data = page.getContent().stream()
				.map(ReportTemplateResource::new)
				.collect(toList());
		this.pageMetadataResource = new PageMetadataResource(page);
	}
}
