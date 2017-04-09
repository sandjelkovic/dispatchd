package com.sandjelkovic.dispatchd.gateway.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MediaUrlDTO {
	@URL
	@NotNull
	private String mediaUrl;
}
