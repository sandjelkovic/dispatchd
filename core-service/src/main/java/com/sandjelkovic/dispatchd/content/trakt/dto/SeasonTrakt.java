package com.sandjelkovic.dispatchd.content.trakt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
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

}
