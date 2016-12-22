package com.sandjelkovic.dispatchd.api.resources;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.ResourceSupport;

public class PageMetadataResource extends ResourceSupport {
	private Page page;

	public PageMetadataResource(Page page) {
		this.page = page;
	}

	public int getTotalPages() {
		return page.getTotalPages();
	}

	public long getTotalElements() {
		return page.getTotalElements();
	}

	public int getNumber() {
		return page.getNumber();
	}

	public int getSize() {
		return page.getSize();
	}

	public int getNumberOfElements() {
		return page.getNumberOfElements();
	}

	public boolean hasContent() {
		return page.hasContent();
	}

	public Sort getSort() {
		return page.getSort();
	}

	public boolean isFirst() {
		return page.isFirst();
	}

	public boolean isLast() {
		return page.isLast();
	}

	public boolean hasNext() {
		return page.hasNext();
	}

	public boolean hasPrevious() {
		return page.hasPrevious();
	}

	public Pageable nextPageable() {
		return page.nextPageable();
	}

	public Pageable previousPageable() {
		return page.previousPageable();
	}
}
