package com.sandjelkovic.dispatchd.gateway.api.processor;

import com.sandjelkovic.dispatchd.gateway.api.link.PageLinksAssembler;
import com.sandjelkovic.dispatchd.gateway.api.resource.PageableListResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author ${sandjelkovic}
 * @date 9.3.17.
 */
@Component
public class PageableListProcessor implements ResourceProcessor<PageableListResource> {
	@Override
	public PageableListResource process(PageableListResource resource) {
		PageLinksAssembler assembler = resource.getLinksAssembler();

		resource.add(assembler.getTemplatedBaseLink().withSelfRel());

		List<Link> links = Stream.of(assembler.getFirstLink(), assembler.getPreviousLink(),
				assembler.getCurrentLink(), assembler.getNextLink(), assembler.getLastLink())
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(toList());
		resource.add(links);
		return resource;
	}
}
