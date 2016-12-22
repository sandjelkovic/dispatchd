package com.sandjelkovic.dispatchd.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TvShowDto {

	private Long id;

	private String title;
	private Integer year;
	private String status;
	@JsonProperty("updated_at")
	private Instant lastUpdatedAt;

	private Map<String, String> ids = new HashMap<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Instant getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(Instant lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public Map<String, String> getIds() {
		return ids;
	}

	public void setIds(Map<String, String> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "TvShowDto{" +
				"id=" + id +
				", title='" + title + '\'' +
				", year=" + year +
				", status='" + status + '\'' +
				", lastUpdatedAt=" + lastUpdatedAt +
				", ids=" + ids +
				'}';
	}
}
