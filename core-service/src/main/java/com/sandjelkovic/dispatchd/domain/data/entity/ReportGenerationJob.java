package com.sandjelkovic.dispatchd.domain.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;


/**
 * The persistent class for the ReportGenerationJob database table.
 */
@Entity
@Table(name = "ReportGenerationJob")
@NamedQuery(name = "ReportGenerationJob.findAll", query = "SELECT r FROM ReportGenerationJob r")
public class ReportGenerationJob extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private JobStatus jobStatus;

	@Column(nullable = false)
	private ZonedDateTime timeToFire;

	@Column(nullable = false)
	private ZonedDateTime timeOfLastFiredJob;

	//bi-directional many-to-one association to ReportTemplate
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "templateId", nullable = false)
	private ReportTemplate reportTemplate;

	//uni-directional many-to-one association to GeneratedReport
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GeneratedreportId", nullable = false)
	private GeneratedReport generatedReport;

	public ReportGenerationJob() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JobStatus getJobStatus() {
		return this.jobStatus;
	}

	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}

	public ZonedDateTime getTimeToFire() {
		return this.timeToFire;
	}

	public void setTimeToFire(ZonedDateTime timeToFire) {
		this.timeToFire = timeToFire;
	}

	public ReportTemplate getReportTemplate() {
		return this.reportTemplate;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public GeneratedReport getGeneratedReport() {
		return this.generatedReport;
	}

	public void setGeneratedReport(GeneratedReport generatedReport) {
		this.generatedReport = generatedReport;
	}

	public ZonedDateTime getTimeOfLastFiredJob() {
		return timeOfLastFiredJob;
	}

	public void setTimeOfLastFiredJob(ZonedDateTime timeOfLastFiredJob) {
		this.timeOfLastFiredJob = timeOfLastFiredJob;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}

	public enum JobStatus {
		NOT_STARTED,
		IN_PROGRESS,
		FINISHED;
	}
}
