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

}
