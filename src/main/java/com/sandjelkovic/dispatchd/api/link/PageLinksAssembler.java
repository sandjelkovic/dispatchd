package com.sandjelkovic.dispatchd.api.link;

import com.sandjelkovic.dispatchd.api.resource.PageMetadataResource;
import com.sandjelkovic.dispatchd.helper.EmptyCollections;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Optional;

public class PageLinksAssembler {

	private final PageMetadataResource pageMetadata;
	private final Optional<Sort> sort;
	private final UriComponentsBuilder baseUri;
	private String pageSize;

	public PageLinksAssembler(PageMetadataResource pageMetadata, ControllerLinkBuilder baseControllerLink) {
		this(pageMetadata, baseControllerLink.toUriComponentsBuilder());
	}

	public PageLinksAssembler(PageMetadataResource pageMetadata, UriComponentsBuilder baseLink) {
		Assert.notNull(pageMetadata);
		Assert.notNull(baseLink);
		this.baseUri = baseLink;
		this.pageMetadata = pageMetadata;
		this.pageSize = String.valueOf(pageMetadata.getSize());
		sort = Optional.ofNullable(pageMetadata.getSort());
	}

	public Link getTemplatedBaseLink() {
		return new Link(prepareHref(getFullyTemplatedUriBuilder()));
	}

	private Link getBaseLinkWithTemplatedParameters() {
		return new Link(prepareHref(getBaseTemplatedUriBuilder()));
	}

	public Optional<Link> getCurrentLink() {
		String pageNumber = String.valueOf(pageMetadata.getNumber());
		Link expandedLink = getBaseLinkWithTemplatedParameters().expand(getParamsForExpansion(pageNumber));
		return Optional.of(expandedLink.withRel(Link.REL_SELF));
	}

	public Optional<Link> getNextLink() {
		if (pageMetadata.hasNext()) {
			String pageNumber = String.valueOf(pageMetadata.nextPageable().getPageNumber());
			Link expandedLink = getBaseLinkWithTemplatedParameters().expand(getParamsForExpansion(pageNumber));
			return Optional.of(expandedLink.withRel(Link.REL_NEXT));
		}
		return Optional.empty();
	}

	public Optional<Link> getPreviousLink() {
		if (pageMetadata.hasPrevious()) {
			String pageNumber = String.valueOf(pageMetadata.getNumber() - 1);
			Link expandedLink = getBaseLinkWithTemplatedParameters().expand(getParamsForExpansion(pageNumber));
			return Optional.of(expandedLink.withRel(Link.REL_PREVIOUS));
		}
		return Optional.empty();
	}

	public Optional<Link> getFirstLink() {
		String pageNumber = String.valueOf(0L);
		Link expandedLink = getBaseLinkWithTemplatedParameters().expand(getParamsForExpansion(pageNumber));
		return Optional.of(expandedLink.withRel(Link.REL_FIRST));
	}

	public Optional<Link> getLastLink() {
		String pageNumber = String.valueOf(pageMetadata.getTotalPages());
		Link expandedLink = getBaseLinkWithTemplatedParameters().expand(getParamsForExpansion(pageNumber));
		return Optional.of(expandedLink.withRel(Link.REL_LAST));
	}

	private Map<String, String> getParamsForExpansion(String pageNumber) {
		Map<String, String> params = EmptyCollections.map();
		params.put("page", pageNumber);
		if (pageSize != null) {
			params.put("size", pageSize);
		}
		sort.ifPresent(s -> params.put("sort", s.toString()));
		return params;
	}

	private String prepareHref(UriComponentsBuilder componentsBuilder) {
		return componentsBuilder.build().toString();
	}

	private UriComponentsBuilder getBaseTemplatedUriBuilder() {
		UriComponentsBuilder uri = baseUri.cloneBuilder()
				.query("page={page}")
				.query("size={size}");
		sort.ifPresent(s -> uri.query("sort={sort}"));
		return uri;
	}

	private UriComponentsBuilder getFullyTemplatedUriBuilder() {
		return baseUri.cloneBuilder()
				.query("page={page}")
				.query("size={size}")
				.query("sort={sort}");
	}
}
