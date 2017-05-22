package com.sandjelkovic.dispatchd.gateway.api.resource;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sandjelkovic.dispatchd.gateway.api.link.PageLinksAssembler;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PageableListResource extends ResourceSupport {
	@JsonIgnore
	private final PageLinksAssembler linksAssembler;
}
