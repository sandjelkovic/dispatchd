package com.sandjelkovic.dispatchd.api.processor;

import com.sandjelkovic.dispatchd.api.PageLinksAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author ${sandjelkovic}
 * @date 11.2.17.
 */
public class PageableResourceLinksProcessor {
	public static void process(ResourceSupport resource, PageLinksAssembler pageLinksAssembler) {
		resource.add(pageLinksAssembler.getTemplatedBaseLink().withSelfRel());

		List<Link> links = Stream.of(pageLinksAssembler.getFirstLink(), pageLinksAssembler.getPreviousLink(),
				pageLinksAssembler.getCurrentLink(), pageLinksAssembler.getNextLink(), pageLinksAssembler.getLastLink())
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(toList());
		resource.add(links);
	}
}
