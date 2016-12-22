package com.sandjelkovic.dispatchd.data.dto;

import java.util.List;

public class ReportDTO {
	private Long id;
	private String text;
	private List<EpisodeDTO> content;
	private Long templateId;

	public ReportDTO() {
	}

	public ReportDTO(Long id, String text, List<EpisodeDTO> content) {
		this.id = id;
		this.text = text;
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<EpisodeDTO> getContent() {
		return content;
	}

	public void setContent(List<EpisodeDTO> content) {
		this.content = content;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

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
