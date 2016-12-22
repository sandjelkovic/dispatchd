package com.sandjelkovic.dispatchd.event;

import com.sandjelkovic.dispatchd.data.entities.ImportStatus;

import java.util.List;

public class ImportTvShowEvent {
	private final List<String> segments;
	private final ImportStatus status;

	public ImportTvShowEvent(List<String> segments, ImportStatus status) {
		this.segments = segments;
		this.status = status;
	}

	public List<String> getSegments() {
		return segments;
	}

	public ImportStatus getStatus() {
		return status;
	}
}
