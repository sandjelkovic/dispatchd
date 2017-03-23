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
public class ShowConnectionsDTO {
	private List<RelationDTO> connections = new ArrayList<>();

	public ShowConnectionsDTO connections(final List<RelationDTO> connections) {
		this.connections = connections;
		return this;
	}

}
