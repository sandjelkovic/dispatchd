package com.sandjelkovic.dispatchd.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EpisodeDTO {

	private Long id;

	private ZonedDateTime airdate;

	@JsonIgnore
	private ZonedDateTime lastUpdated;

	private String customNumbering;

	private String description;

	private Integer number;

	private String seasonNumber;

	private String title;

	private String imdbId;

	private String tmdbId;

	private String traktId;

	private String tvdbId;

	private Long seasonId;

	private Long tvShowId;

	public EpisodeDTO id(final Long id) {
		this.id = id;
		return this;
	}

	public EpisodeDTO airdate(final ZonedDateTime airdate) {
		this.airdate = airdate;
		return this;
	}

	public EpisodeDTO lastUpdated(final ZonedDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
		return this;
	}

	public EpisodeDTO customNumbering(final String customNumbering) {
		this.customNumbering = customNumbering;
		return this;
	}

	public EpisodeDTO description(final String description) {
		this.description = description;
		return this;
	}

	public EpisodeDTO number(final Integer number) {
		this.number = number;
		return this;
	}

	public EpisodeDTO seasonNumber(final String seasonNumber) {
		this.seasonNumber = seasonNumber;
		return this;
	}

	public EpisodeDTO title(final String title) {
		this.title = title;
		return this;
	}

	public EpisodeDTO imdbId(final String imdbId) {
		this.imdbId = imdbId;
		return this;
	}

	public EpisodeDTO tmdbId(final String tmdbId) {
		this.tmdbId = tmdbId;
		return this;
	}

	public EpisodeDTO traktId(final String traktId) {
		this.traktId = traktId;
		return this;
	}

	public EpisodeDTO tvdbId(final String tvdbId) {
		this.tvdbId = tvdbId;
		return this;
	}

	public EpisodeDTO seasonId(final Long seasonId) {
		this.seasonId = seasonId;
		return this;
	}

	public EpisodeDTO tvShowId(final Long tvShowId) {
		this.tvShowId = tvShowId;
		return this;
	}
}
