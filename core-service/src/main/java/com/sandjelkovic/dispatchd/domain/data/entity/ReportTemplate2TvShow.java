package com.sandjelkovic.dispatchd.domain.data.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * The persistent class for the ReportTemplate2TvShow database table.
 */
@Entity
@Table(name = "ReportTemplate2TvShow")
@NamedQuery(name = "ReportTemplate2TvShow.findAll", query = "SELECT s FROM ReportTemplate2TvShow s")
public class ReportTemplate2TvShow extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ReportTemplate2TvShowPK id;

	@Column(nullable = false)
	@NotNull
	private Integer orderInReport = 0;

	//bi-directional many-to-one association to TvShow
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TvShowId", nullable = false, insertable = false, updatable = false)
	private TvShow tvShow;

	//bi-directional many-to-one association to ReportTemplate
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ReporttemplateId", nullable = false, insertable = false, updatable = false)
	private ReportTemplate reportTemplate;

	public ReportTemplate2TvShow() {
	}

	public ReportTemplate2TvShowPK getId() {
		return this.id;
	}

	public void setId(ReportTemplate2TvShowPK id) {
		this.id = id;
	}

	public Integer getOrderInReport() {
		return this.orderInReport;
	}

	public void setOrderInReport(Integer orderInReport) {
		this.orderInReport = orderInReport;
	}

	public TvShow getTvShow() {
		return this.tvShow;
	}

	public void setTvShow(TvShow tvShow) {
		this.tvShow = tvShow;
	}

	public ReportTemplate getReportTemplate() {
		return this.reportTemplate;
	}

	public void setReportTemplate(ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public ReportTemplate2TvShow id(final ReportTemplate2TvShowPK id) {
		this.id = id;
		return this;
	}

	public ReportTemplate2TvShow orderInReport(final Integer orderInReport) {
		this.orderInReport = orderInReport;
		return this;
	}

	public ReportTemplate2TvShow tvShow(final TvShow tvShow) {
		this.tvShow = tvShow;
		return this;
	}

	public ReportTemplate2TvShow reportTemplate(final ReportTemplate reportTemplate) {
		this.reportTemplate = reportTemplate;
		return this;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}
}
