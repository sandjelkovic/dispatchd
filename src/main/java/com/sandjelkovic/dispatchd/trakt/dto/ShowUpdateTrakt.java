package com.sandjelkovic.dispatchd.trakt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

/**
 * @author ${sandjelkovic}
 * @date 5.2.17.
 */
public class ShowUpdateTrakt {
	@JsonProperty("updated_at")
	private ZonedDateTime updatedAt;
	private TvShowTrakt show;

	public ShowUpdateTrakt updatedAt(final ZonedDateTime updatedAt) {
		this.updatedAt = updatedAt;
		return this;
	}

	public ShowUpdateTrakt show(final TvShowTrakt show) {
		this.show = show;
		return this;
	}

	public ZonedDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(ZonedDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public TvShowTrakt getShow() {
		return show;
	}

	public void setShow(TvShowTrakt show) {
		this.show = show;
	}

	@Override
	public String toString() {
		return "ShowUpdateTrakt{" +
				"updatedAt=" + updatedAt +
				", show=" + show +
				'}';
	}
}
