package com.sandjelkovic.dispatchd.data.dto;

/**
 * Created by stefan on 21.1.17..
 */
public class RelationDto {
	private Long id;
	private Integer order;

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RelationDto id(final Long id) {
		this.id = id;
		return this;
	}

	public RelationDto order(final Integer order) {
		this.order = order;
		return this;
	}

}
