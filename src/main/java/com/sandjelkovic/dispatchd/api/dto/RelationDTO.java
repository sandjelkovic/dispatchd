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
public class RelationDTO {
	private Long targetId;
	private Long sourceId;
	private int order;

	public RelationDTO id(final Long id) {
		this.targetId = id;
		return this;
	}

	public RelationDTO order(final int order) {
		this.order = order;
		return this;
	}

	public RelationDTO sourceId(final Long sourceId) {
		this.sourceId = sourceId;
		return this;
	}

}
