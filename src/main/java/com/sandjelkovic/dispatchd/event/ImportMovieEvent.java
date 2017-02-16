package com.sandjelkovic.dispatchd.event;

import com.sandjelkovic.dispatchd.domain.data.entity.ImportStatus;

import java.util.List;

public class ImportMovieEvent {
	private final List<String> segments;
	private final ImportStatus status;

	public ImportMovieEvent(List<String> segments, ImportStatus status) {
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
