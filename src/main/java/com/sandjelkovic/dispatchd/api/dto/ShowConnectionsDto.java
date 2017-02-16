package com.sandjelkovic.dispatchd.api.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefan on 21.1.17..
 */
public class ShowConnectionsDto {
	private List<RelationDto> connections = new ArrayList<>();

	public List<RelationDto> getConnections() {
		return connections;
	}

	public void setConnections(List<RelationDto> connections) {
		this.connections = connections;
	}

	public ShowConnectionsDto connections(final List<RelationDto> connections) {
		this.connections = connections;
		return this;
	}

}
