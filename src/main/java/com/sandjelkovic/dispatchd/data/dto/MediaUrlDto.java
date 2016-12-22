package com.sandjelkovic.dispatchd.data.dto;

import org.hibernate.validator.constraints.URL;

public class MediaUrlDto {
	@URL
	private String mediaUrl;

	public MediaUrlDto(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public MediaUrlDto() {
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
}
