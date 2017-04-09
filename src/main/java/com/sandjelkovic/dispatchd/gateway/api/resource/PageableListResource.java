package com.sandjelkovic.dispatchd.gateway.api.resource;

import com.sandjelkovic.dispatchd.gateway.api.link.PageLinksAssembler;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PageableListResource extends ResourceSupport {
	private final PageLinksAssembler linksAssembler;
}
