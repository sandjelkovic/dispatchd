package com.sandjelkovic.dispatchd.api.processor;

import com.sandjelkovic.dispatchd.api.controller.ReportController;
import com.sandjelkovic.dispatchd.api.controller.ReportTemplateController;
import com.sandjelkovic.dispatchd.api.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.api.link.LinkAssembler;
import com.sandjelkovic.dispatchd.api.link.RelNamesConstants;
import com.sandjelkovic.dispatchd.api.resource.ReportTemplateResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author ${sandjelkovic}
 * @date 26.2.17.
 */
@Component
public class ReportTemplateResourceProcessor implements ResourceProcessor<ReportTemplateResource> {
	@Override
	public ReportTemplateResource process(ReportTemplateResource resource) {
		ReportTemplateDTO content = resource.getData();

		resource.add(linkTo(methodOn(ReportTemplateController.class).getTemplate(content.getId())).withSelfRel());
		resource.add(LinkAssembler.getPageableTemplatedBaseLink(linkTo(ReportTemplateController.class)).withRel(RelNamesConstants.USER_REPORT_TEMPLATES));
		resource.add(linkTo(ReportController.class).withRel(RelNamesConstants.USER_REPORTS));
		String hrefToReportTemplate = LinkAssembler.hrefFromComponentBuilder(linkTo(ReportController.class).toUriComponentsBuilder()
				.queryParam("templateId", content.getId()));
		resource.add(new Link(hrefToReportTemplate, RelNamesConstants.REPORTS_OF_TEMPLATE));
		return resource;
	}
}
