package com.sandjelkovic.dispatchd.gateway.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sandjelkovic.dispatchd.common.helper.EmptyCollections;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class TvShowDTO {

	private Long id;

	private String title;
	private Integer year;
	private String status;
	@JsonProperty("updated_at")
	private Instant lastUpdatedAt;

	private Map<String, String> ids = EmptyCollections.map();
}
