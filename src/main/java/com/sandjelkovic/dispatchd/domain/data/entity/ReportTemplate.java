package com.sandjelkovic.dispatchd.domain.data.entity;

import com.sandjelkovic.dispatchd.helper.EmptyCollections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;


/**
 * The persistent class for the ReportTemplate database table.
 */
@Entity
@Table(name = "ReportTemplate")
@NamedQuery(name = "ReportTemplate.findAll", query = "SELECT r FROM ReportTemplate r")
public class ReportTemplate extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(nullable = false)
	@NotNull
	private boolean active;

	@Column(nullable = true, length = 2000)
	private String description;

	@Column(nullable = false, length = 100)
	@NotNull
	private String name;

	@Column(nullable = true)
	private ChronoUnit repeatInterval;

	@Column(nullable = false)
	@NotNull
	private DayOfWeek repeatDayOfWeek;

	@Column(nullable = false)
	@NotNull
	@Min(1)
	@Max(28)
	private Integer repeatDayOfMonth;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	@NotNull
	private ReportRepeatType repeatType;

	@Column(nullable = false)
	@NotNull
	private LocalTime timeOfDayToDeliver;

	@Column(nullable = false)
	@NotNull
	private ZonedDateTime timeToGenerateReport; // to generate Report

	@Column(nullable = false)
	@NotNull
	private ZonedDateTime timeOfLastGeneratedReport; // to of last generated Report

	//bi-directional many-to-one association to ReportGenerationJob
	@OneToMany(mappedBy = "reportTemplate")
	private List<ReportGenerationJob> reportGenerationJobs;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = true)
	private User user;

	//bi-directional many-to-one association to ReportTemplate2TvShow
	@OneToMany(mappedBy = "reportTemplate")
	private List<ReportTemplate2TvShow> reportTemplate2TvShows = EmptyCollections.list();

	public ReportTemplate() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean getActive() {
		return this.active;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalTime getTimeOfDayToDeliver() {
		return timeOfDayToDeliver;
	}

	public void setTimeOfDayToDeliver(LocalTime timeOfDayToDeliver) {
		this.timeOfDayToDeliver = timeOfDayToDeliver;
	}

	public ReportRepeatType getRepeatType() {
		return repeatType;
	}

	public void setRepeatType(ReportRepeatType repeatType) {
		this.repeatType = repeatType;
	}

	public DayOfWeek getRepeatDayOfWeek() {
		return repeatDayOfWeek;
	}

	public void setRepeatDayOfWeek(DayOfWeek repeatDayOfWeek) {
		this.repeatDayOfWeek = repeatDayOfWeek;
	}

	public ChronoUnit getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(ChronoUnit repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public ZonedDateTime getTimeToGenerateReport() {
		return this.timeToGenerateReport;
	}

	public void setTimeToGenerateReport(ZonedDateTime timeToGenerateReport) {
		this.timeToGenerateReport = timeToGenerateReport;
	}

	public Integer getRepeatDayOfMonth() {
		return repeatDayOfMonth;
	}

	public void setRepeatDayOfMonth(Integer repeatDayOfMonth) {
		this.repeatDayOfMonth = repeatDayOfMonth;
	}

	public ZonedDateTime getTimeOfLastGeneratedReport() {
		return timeOfLastGeneratedReport;
	}

	public void setTimeOfLastGeneratedReport(ZonedDateTime timeOfLastGeneratedReport) {
		this.timeOfLastGeneratedReport = timeOfLastGeneratedReport;
	}

	public List<ReportGenerationJob> getReportGenerationJobs() {
		return this.reportGenerationJobs;
	}

	public void setReportGenerationJobs(List<ReportGenerationJob> reportGenerationJobs) {
		this.reportGenerationJobs = reportGenerationJobs;
	}

	public ReportGenerationJob addReportGenerationJob(ReportGenerationJob reportGenerationJob) {
		getReportGenerationJobs().add(reportGenerationJob);
		reportGenerationJob.setReportTemplate(this);

		return reportGenerationJob;
	}

	public ReportGenerationJob removeReportGenerationJob(ReportGenerationJob reportGenerationJob) {
		getReportGenerationJobs().remove(reportGenerationJob);
		reportGenerationJob.setReportTemplate(null);

		return reportGenerationJob;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ReportTemplate2TvShow> getReportTemplate2TvShows() {
		return this.reportTemplate2TvShows;
	}

	public void setReportTemplate2TvShows(List<ReportTemplate2TvShow> reportTemplate2TvShows) {
		this.reportTemplate2TvShows = reportTemplate2TvShows;
	}

	public ReportTemplate2TvShow addShowsReportTemplate(ReportTemplate2TvShow showsReportTemplate) {
		getReportTemplate2TvShows().add(showsReportTemplate);
		showsReportTemplate.setReportTemplate(this);

		return showsReportTemplate;
	}

	public ReportTemplate2TvShow removeShowsReportTemplate(ReportTemplate2TvShow showsReportTemplate) {
		getReportTemplate2TvShows().remove(showsReportTemplate);
		showsReportTemplate.setReportTemplate(null);

		return showsReportTemplate;
	}

	public ReportTemplate id(final Long id) {
		this.id = id;
		return this;
	}

	public ReportTemplate active(final boolean active) {
		this.active = active;
		return this;
	}

	public ReportTemplate description(final String description) {
		this.description = description;
		return this;
	}

	public ReportTemplate name(final String name) {
		this.name = name;
		return this;
	}

	public ReportTemplate repeatInterval(final ChronoUnit repeatInterval) {
		this.repeatInterval = repeatInterval;
		return this;
	}

	public ReportTemplate repeatDayOfWeek(final DayOfWeek repeatDayOfWeek) {
		this.repeatDayOfWeek = repeatDayOfWeek;
		return this;
	}

	public ReportTemplate repeatDayOfMonth(final Integer repeatDayOfMonth) {
		this.repeatDayOfMonth = repeatDayOfMonth;
		return this;
	}

	public ReportTemplate repeatType(final ReportRepeatType repeatType) {
		this.repeatType = repeatType;
		return this;
	}

	public ReportTemplate timeOfDayToDeliver(final LocalTime timeOfDayToDeliver) {
		this.timeOfDayToDeliver = timeOfDayToDeliver;
		return this;
	}

	public ReportTemplate timeToGenerateReport(final ZonedDateTime timeToGenerateReport) {
		this.timeToGenerateReport = timeToGenerateReport;
		return this;
	}

	public ReportTemplate timeOfLastGeneratedReport(final ZonedDateTime timeOfLastGeneratedReport) {
		this.timeOfLastGeneratedReport = timeOfLastGeneratedReport;
		return this;
	}

	public ReportTemplate reportGenerationJobs(final List<ReportGenerationJob> reportGenerationJobs) {
		this.reportGenerationJobs = reportGenerationJobs;
		return this;
	}

	public ReportTemplate user(final User user) {
		this.user = user;
		return this;
	}

	public ReportTemplate reportTemplate2TvShows(final List<ReportTemplate2TvShow> reportTemplate2TvShows) {
		this.reportTemplate2TvShows = reportTemplate2TvShows;
		return this;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}
}
