package com.sandjelkovic.dispatchd.trakt.dto;


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

}
