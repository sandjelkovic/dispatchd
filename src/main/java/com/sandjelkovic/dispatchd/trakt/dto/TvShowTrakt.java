package com.sandjelkovic.dispatchd.trakt.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class TvShowTrakt {
	private String title;
	private Integer year;
	private String status;
	@JsonProperty("updated_at")
	private Instant updatedAt;
	private String overview;
	private Map<String, String> ids = new HashMap<>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Map<String, String> getIds() {
		return ids;
	}

	public void setIds(Map<String, String> ids) {
		this.ids = ids;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	@Override
	public String toString() {
		return "TvShowTrakt{" +
				"title='" + title + '\'' +
				", year=" + year +
				", status='" + status + '\'' +
				", updatedAt=" + updatedAt +
				", overview='" + overview + '\'' +
				", ids=" + ids +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TvShowTrakt that = (TvShowTrakt) o;

		if (title != null ? !title.equals(that.title) : that.title != null) return false;
		if (year != null ? !year.equals(that.year) : that.year != null) return false;
		if (status != null ? !status.equals(that.status) : that.status != null) return false;
		if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;
		if (overview != null ? !overview.equals(that.overview) : that.overview != null) return false;
		return ids != null ? ids.equals(that.ids) : that.ids == null;

	}

	@Override
	public int hashCode() {
		int result = title != null ? title.hashCode() : 0;
		result = 31 * result + (year != null ? year.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
		result = 31 * result + (overview != null ? overview.hashCode() : 0);
		result = 31 * result + (ids != null ? ids.hashCode() : 0);
		return result;
	}
}
