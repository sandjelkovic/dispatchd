package com.sandjelkovic.dispatchd.api.resource;

import com.sandjelkovic.dispatchd.api.link.PageLinksAssembler;
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
