package com.sandjelkovic.dispatchd.domain.data.entity;

public enum ImportProgressStatus {
	QUEUED("QUEUED"),
	IN_PROGRESS("IN_PROGRESS"),
	SUCCESS("SUCCESS"),
	SHOW_ALREADY_EXISTS("SHOW_ALREADY_EXISTS"),
	REMOTE_SERVER_ERROR("REMOTE_SERVER_ERROR"),
	ERROR("ERROR");

	private final String field;

	ImportProgressStatus(String field) {
		this.field = field;
	}

	@Override
	public String toString() {
		return field;
	}
}
