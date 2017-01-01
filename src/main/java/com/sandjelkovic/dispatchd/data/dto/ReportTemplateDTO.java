package com.sandjelkovic.dispatchd.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sandjelkovic.dispatchd.data.entities.ReportRepeatType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class ReportTemplateDTO {
	private Long id;

	@NotNull
	private Boolean active;

	@Length(max = 255)
	private String description;

	@NotNull
	@NotBlank
	@Length(max = 100)
	private String name;

	@NotNull
	private DayOfWeek repeatDayOfWeek;

	@Min(1)
	@Max(28)
	private Integer repeatDayOfMonth;

	@NotNull
	private ReportRepeatType repeatType;

	@NotNull
	private LocalTime timeOfDayToDeliver;

	@JsonIgnore
	private String username;

	private List<TvShowDto> tvShows;

	public ReportTemplateDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DayOfWeek getRepeatDayOfWeek() {
		return repeatDayOfWeek;
	}

	public void setRepeatDayOfWeek(DayOfWeek repeatDayOfWeek) {
		this.repeatDayOfWeek = repeatDayOfWeek;
	}

	public Integer getRepeatDayOfMonth() {
		return repeatDayOfMonth;
	}

	public void setRepeatDayOfMonth(Integer repeatDayOfMonth) {
		this.repeatDayOfMonth = repeatDayOfMonth;
	}

	public ReportRepeatType getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(ReportRepeatType repeatType) {
		this.repeatType = repeatType;
	}

	public LocalTime getTimeOfDayToDeliver() {
		return timeOfDayToDeliver;
	}

	public void setTimeOfDayToDeliver(LocalTime timeOfDayToDeliver) {
		this.timeOfDayToDeliver = timeOfDayToDeliver;
	}

	public List<TvShowDto> getTvShows() {
		return tvShows;
	}

	public void setTvShows(List<TvShowDto> tvShows) {
		this.tvShows = tvShows;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ReportTemplateDTO id(final Long id) {
		this.id = id;
		return this;
	}

	public ReportTemplateDTO active(final Boolean active) {
		this.active = active;
		return this;
	}

	public ReportTemplateDTO description(final String description) {
		this.description = description;
		return this;
	}

	public ReportTemplateDTO name(final String name) {
		this.name = name;
		return this;
	}

	public ReportTemplateDTO repeatDayOfWeek(final DayOfWeek repeatDayOfWeek) {
		this.repeatDayOfWeek = repeatDayOfWeek;
		return this;
	}

	public ReportTemplateDTO repeatDayOfMonth(final Integer repeatDayOfMonth) {
		this.repeatDayOfMonth = repeatDayOfMonth;
		return this;
	}

	public ReportTemplateDTO repeatType(final ReportRepeatType repeatType) {
		this.repeatType = repeatType;
		return this;
	}

	public ReportTemplateDTO timeOfDayToDeliver(final LocalTime timeOfDayToDeliver) {
		this.timeOfDayToDeliver = timeOfDayToDeliver;
		return this;
	}

	public ReportTemplateDTO tvShows(final List<TvShowDto> tvShows) {
		this.tvShows = tvShows;
		return this;
	}

	public ReportTemplateDTO username(String username) {
		this.username = username;
		return this;
	}
}