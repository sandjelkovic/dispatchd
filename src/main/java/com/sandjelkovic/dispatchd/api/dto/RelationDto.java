package com.sandjelkovic.dispatchd.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by stefan on 21.1.17..
 */

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class RelationDto {
	private Long id;
	private Integer order;

	public RelationDto id(final Long id) {
		this.id = id;
		return this;
	}

	public RelationDto order(final Integer order) {
		this.order = order;
		return this;
	}

}
