package com.sandjelkovic.dispatchd.content.trakt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

/**
 * @author ${sandjelkovic}
 * @date 5.2.17.
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ShowUpdateTrakt {
	@JsonProperty("updated_at")
	private ZonedDateTime updatedAt;
	private TvShowTrakt show;
}
