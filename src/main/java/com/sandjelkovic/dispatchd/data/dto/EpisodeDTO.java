package com.sandjelkovic.dispatchd.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.ZonedDateTime;

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

	public EpisodeDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ZonedDateTime getAirdate() {
		return airdate;
	}

	public void setAirdate(ZonedDateTime airdate) {
		this.airdate = airdate;
	}

	public ZonedDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(ZonedDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getCustomNumbering() {
		return customNumbering;
	}

	public void setCustomNumbering(String customNumbering) {
		this.customNumbering = customNumbering;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getSeasonNumber() {
		return seasonNumber;
	}

	public void setSeasonNumber(String seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTmdbId() {
		return tmdbId;
	}

	public void setTmdbId(String tmdbId) {
		this.tmdbId = tmdbId;
	}

	public String getTraktId() {
		return traktId;
	}

	public void setTraktId(String traktId) {
		this.traktId = traktId;
	}

	public String getTvdbId() {
		return tvdbId;
	}

	public void setTvdbId(String tvdbId) {
		this.tvdbId = tvdbId;
	}

	public Long getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(Long seasonId) {
		this.seasonId = seasonId;
	}

	public Long getTvShowId() {
		return tvShowId;
	}

	public void setTvShowId(Long tvShowId) {
		this.tvShowId = tvShowId;
	}

	public String getWholeNumbering() {
		return "s" + seasonNumber + "e" + number;
	}

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
