package com.sandjelkovic.dispatchd.gateway.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ReportDTO {
	private Long id;
	private String text;
	private List<EpisodeDTO> content;
	private Long templateId;

	public ReportDTO id(final Long id) {
		this.id = id;
		return this;
	}

	public ReportDTO text(final String text) {
		this.text = text;
		return this;
	}

	public ReportDTO content(final List<EpisodeDTO> content) {
		this.content = content;
		return this;
	}

	public ReportDTO templateId(final Long templateId) {
		this.templateId = templateId;
		return this;
	}
}
