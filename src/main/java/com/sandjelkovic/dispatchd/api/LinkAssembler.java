package com.sandjelkovic.dispatchd.api;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class LinkAssembler {

	public static Link getPageableTemplatedBaseLink(ControllerLinkBuilder baseLinkBuilder) {
		return new Link(hrefFromComponentBuilder(getFullyTemplatedUriBuilder(baseLinkBuilder)));
	}

	public static String hrefFromComponentBuilder(UriComponentsBuilder componentsBuilder) {
		return componentsBuilder.build().toString();
	}

	private static UriComponentsBuilder getFullyTemplatedUriBuilder(ControllerLinkBuilder baseLinkBuilder) {
		return baseLinkBuilder.toUriComponentsBuilder()
				.query("page={page}")
				.query("size={size}")
				.query("sort={sort}");
	}
}
