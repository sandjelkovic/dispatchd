package com.sandjelkovic.dispatchd.gateway.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowDTO {

	private Long id;

	private String title;
	private String description;
	private Integer year;
	private String status;
	private ZonedDateTime lastUpdatedAt;

	private String imdbId;

	private String tmdbId;

	private String traktId;

	private String tvdbId;
}
