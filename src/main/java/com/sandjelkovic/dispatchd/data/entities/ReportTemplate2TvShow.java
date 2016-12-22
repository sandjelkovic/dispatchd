package com.sandjelkovic.dispatchd.data.entities;

import javax.persistence.*;
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

	@Override
	protected Object getInternalId() {
		return getId();
	}
}
