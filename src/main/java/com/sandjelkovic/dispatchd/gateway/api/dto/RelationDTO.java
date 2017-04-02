package com.sandjelkovic.dispatchd.gateway.api.dto;

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
	private String targetId;
	private String sourceId;
	// force default => int instead of Integer
	private int order;

	public RelationDTO id(final String id) {
		this.targetId = id;
		return this;
	}

	public RelationDTO order(final int order) {
		this.order = order;
		return this;
	}

	public RelationDTO sourceId(final String sourceId) {
		this.sourceId = sourceId;
		return this;
	}

}
