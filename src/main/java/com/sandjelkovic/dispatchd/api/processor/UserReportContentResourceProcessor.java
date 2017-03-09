package com.sandjelkovic.dispatchd.api.processor;

import com.sandjelkovic.dispatchd.api.controller.ReportController;
import com.sandjelkovic.dispatchd.api.controller.ReportTemplateController;
import com.sandjelkovic.dispatchd.api.link.LinkAssembler;
import com.sandjelkovic.dispatchd.api.link.RelNamesConstants;
import com.sandjelkovic.dispatchd.api.resource.UserReportContentResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author ${sandjelkovic}
 * @date 9.3.17.
 */
@Component
public class UserReportContentResourceProcessor implements ResourceProcessor<UserReportContentResource> {
	@Override
	public UserReportContentResource process(UserReportContentResource resource) {
		Long reportId = resource.getReportDTO().getId();
		ControllerLinkBuilder reportContentsBase = linkTo(methodOn(ReportController.class).getReportContents(reportId, null));
		resource.add(LinkAssembler.getPageableTemplatedBaseLink(reportContentsBase).withRel(Link.REL_SELF));
		resource.add(linkTo(methodOn(ReportTemplateController.class).getTemplate(reportId)).withRel(RelNamesConstants.REPORT));
		resource.add(LinkAssembler.getPageableTemplatedBaseLink(linkTo(ReportController.class)).withRel(RelNamesConstants.USER_REPORTS));
		Long templateId = resource.getReportDTO().getTemplateId();
		resource.add(linkTo(methodOn(ReportTemplateController.class).getTemplate(templateId)).withRel(RelNamesConstants.TEMPLATE));
		return resource;
	}
}
