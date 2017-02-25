package com.sandjelkovic.dispatchd.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MediaUrlDto {
	@URL
	private String mediaUrl;
}
