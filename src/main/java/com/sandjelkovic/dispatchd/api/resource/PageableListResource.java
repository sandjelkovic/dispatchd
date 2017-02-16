package com.sandjelkovic.dispatchd.api.resource;

import com.sandjelkovic.dispatchd.api.link.PageLinksAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class PageableListResource extends ResourceSupport {
	public PageableListResource(PageLinksAssembler pageLinksAssembler) {
		this.add(pageLinksAssembler.getTemplatedBaseLink().withSelfRel());

		List<Link> links = Stream.of(pageLinksAssembler.getFirstLink(), pageLinksAssembler.getPreviousLink(),
				pageLinksAssembler.getCurrentLink(), pageLinksAssembler.getNextLink(), pageLinksAssembler.getLastLink())
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(toList());
		this.add(links);
	}
}
