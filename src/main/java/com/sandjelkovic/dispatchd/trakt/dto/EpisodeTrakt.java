package com.sandjelkovic.dispatchd.trakt.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class EpisodeTrakt {
	private String season;
	private Integer number;
	private String title;
	private Map<String, String> ids = new HashMap<>();
	private String overview;
	@JsonProperty("updated_at")
	private Instant updatedAt;
	@JsonProperty("first_aired")
	private Instant firstAired;

	public String getSeason() {
		return season;
	}

	public Integer getNumber() {
		return number;
	}

	public String getTitle() {
		return title;
	}

	public Map<String, String> getIds() {
		return ids;
	}

	public String getOverview() {
		return overview;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public Instant getFirstAired() {
		return firstAired;
	}

	@Override
	public String toString() {
		return "EpisodeTrakt{" +
				"season='" + season + '\'' +
				", number=" + number +
				", title='" + title + '\'' +
				", ids=" + ids +
				", overview='" + overview + '\'' +
				", updatedAt=" + updatedAt +
				", firstAired=" + firstAired +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EpisodeTrakt that = (EpisodeTrakt) o;

		if (season != null ? !season.equals(that.season) : that.season != null) return false;
		if (number != null ? !number.equals(that.number) : that.number != null) return false;
		if (title != null ? !title.equals(that.title) : that.title != null) return false;
		if (ids != null ? !ids.equals(that.ids) : that.ids != null) return false;
		if (overview != null ? !overview.equals(that.overview) : that.overview != null) return false;
		if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;
		return firstAired != null ? firstAired.equals(that.firstAired) : that.firstAired == null;

	}

	@Override
	public int hashCode() {
		int result = season != null ? season.hashCode() : 0;
		result = 31 * result + (number != null ? number.hashCode() : 0);
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (ids != null ? ids.hashCode() : 0);
		result = 31 * result + (overview != null ? overview.hashCode() : 0);
		result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
		result = 31 * result + (firstAired != null ? firstAired.hashCode() : 0);
		return result;
	}
}
