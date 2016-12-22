package com.sandjelkovic.dispatchd.api.resources;

import com.sandjelkovic.dispatchd.api.PageLinksAssembler;
import com.sandjelkovic.dispatchd.api.RelNamesConstants;
import org.springframework.hateoas.ResourceSupport;

import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class PageableListResource extends ResourceSupport {
	public PageableListResource(PageLinksAssembler pageLinksAssembler) {
		this.add(pageLinksAssembler.getTemplatedBaseLink().withRel(RelNamesConstants.USER_REPORT_TEMPLATES));
		this.add(Stream.of(pageLinksAssembler.getFirstLink(), pageLinksAssembler.getPreviousLink(),
				pageLinksAssembler.getCurrentLink(), pageLinksAssembler.getNextLink(), pageLinksAssembler.getLastLink())
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(toList()));
	}
}
