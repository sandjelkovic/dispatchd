package com.sandjelkovic.dispatchd.api.processor;

import com.sandjelkovic.dispatchd.api.controller.ReportController;
import com.sandjelkovic.dispatchd.api.controller.ReportTemplateController;
import com.sandjelkovic.dispatchd.api.link.LinkAssembler;
import com.sandjelkovic.dispatchd.api.link.RelNamesConstants;
import com.sandjelkovic.dispatchd.api.resource.UserReportListResource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author ${sandjelkovic}
 * @date 9.3.17.
 */
@Component
public class UserReportListResourceProcessor implements ResourceProcessor<UserReportListResource> {

	@Override
	public UserReportListResource process(UserReportListResource resource) {
		resource.add(LinkAssembler.getPageableTemplatedBaseLink(linkTo(ReportController.class)).withSelfRel());
		resource.add(LinkAssembler.getPageableTemplatedBaseLink(linkTo(ReportController.class)).withRel(RelNamesConstants.USER_REPORTS));
		resource.add(linkTo(ReportTemplateController.class).withRel(RelNamesConstants.USER_REPORT_TEMPLATES));
		return resource;
	}
}
