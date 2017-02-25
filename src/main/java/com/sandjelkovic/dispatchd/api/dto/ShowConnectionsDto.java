package com.sandjelkovic.dispatchd.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefan on 21.1.17..
 */

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ShowConnectionsDto {
	private List<RelationDto> connections = new ArrayList<>();

	public ShowConnectionsDto connections(final List<RelationDto> connections) {
		this.connections = connections;
		return this;
	}

}
