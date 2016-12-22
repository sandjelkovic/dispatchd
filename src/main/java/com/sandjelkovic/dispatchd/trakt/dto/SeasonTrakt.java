package com.sandjelkovic.dispatchd.trakt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class SeasonTrakt {

	private String number;

	private Map<String, String> ids = new HashMap<>();

	private String overview;

	@JsonProperty("first_aired")
	private Instant firstAired;

	@JsonProperty("episode_count")
	private Integer episodeCount;

	@JsonProperty("aired_episodes")
	private Integer airedEpisodes;

	public String getNumber() {
		return number;
	}

	public Map<String, String> getIds() {
		return ids;
	}

	public String getOverview() {
		return overview;
	}

	public Instant getFirstAired() {
		return firstAired;
	}

	public Integer getEpisodeCount() {
		return episodeCount;
	}

	public Integer getAiredEpisodes() {
		return airedEpisodes;
	}

	@Override
	public String toString() {
		return "SeasonTrakt{" +
				"number='" + number + '\'' +
				", ids=" + ids +
				", overview='" + overview + '\'' +
				", firstAired=" + firstAired +
				", episodeCount=" + episodeCount +
				", airedEpisodes=" + airedEpisodes +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SeasonTrakt that = (SeasonTrakt) o;

		if (number != null ? !number.equals(that.number) : that.number != null) return false;
		if (ids != null ? !ids.equals(that.ids) : that.ids != null) return false;
		if (overview != null ? !overview.equals(that.overview) : that.overview != null) return false;
		if (firstAired != null ? !firstAired.equals(that.firstAired) : that.firstAired != null) return false;
		if (episodeCount != null ? !episodeCount.equals(that.episodeCount) : that.episodeCount != null) return false;
		return airedEpisodes != null ? airedEpisodes.equals(that.airedEpisodes) : that.airedEpisodes == null;

	}

	@Override
	public int hashCode() {
		int result = number != null ? number.hashCode() : 0;
		result = 31 * result + (ids != null ? ids.hashCode() : 0);
		result = 31 * result + (overview != null ? overview.hashCode() : 0);
		result = 31 * result + (firstAired != null ? firstAired.hashCode() : 0);
		result = 31 * result + (episodeCount != null ? episodeCount.hashCode() : 0);
		result = 31 * result + (airedEpisodes != null ? airedEpisodes.hashCode() : 0);
		return result;
	}
}
