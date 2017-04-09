package com.sandjelkovic.dispatchd.gateway.api.processor;

import com.sandjelkovic.dispatchd.gateway.api.controller.ReportController;
import com.sandjelkovic.dispatchd.gateway.api.controller.ReportTemplateController;
import com.sandjelkovic.dispatchd.gateway.api.dto.ReportDTO;
import com.sandjelkovic.dispatchd.gateway.api.link.LinkAssembler;
import com.sandjelkovic.dispatchd.gateway.api.link.RelNamesConstants;
import com.sandjelkovic.dispatchd.gateway.api.resource.ReportResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author ${sandjelkovic}
 * @date 26.2.17.
 */
@Component
public class ReportResourceProcessor implements ResourceProcessor<ReportResource> {
	@Override
	public ReportResource process(ReportResource resource) {
		ReportDTO content = resource.getData();

		ControllerLinkBuilder reportContentsBase = ControllerLinkBuilder.linkTo(methodOn(ReportController.class).getReportContents(content.getId(), null));
		resource.add(LinkAssembler.getPageableTemplatedBaseLink(reportContentsBase).withRel(RelNamesConstants.REPORT_CONTENTS));

		resource.add(ControllerLinkBuilder.linkTo(methodOn(ReportController.class).getReport(content.getId())).withSelfRel());
		resource.add(LinkAssembler.getPageableTemplatedBaseLink(linkTo(ReportController.class)).withRel(RelNamesConstants.USER_REPORTS));

		resource.add(ControllerLinkBuilder.linkTo(methodOn(ReportTemplateController.class).getTemplate(content.getTemplateId())).withRel(RelNamesConstants.TEMPLATE));
		return resource;
	}
}
