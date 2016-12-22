package com.sandjelkovic.dispatchd.data.entities;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the GeneratedReportContent database table.
 */
@Entity
@Table(name = "GeneratedReportContent")
@NamedQuery(name = "GeneratedReportContent.findAll", query = "SELECT g FROM GeneratedReportContent g")
public class GeneratedReportContent extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GeneratedReportContentPK id;

	@Column(nullable = false)
	private int orderInReport;

	//bi-directional many-to-one association to GeneratedReport
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GeneratedreportId", nullable = false, insertable = false, updatable = false)
	private GeneratedReport generatedReport;

	//uni-directional many-to-one association to Episode
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EpisodeId", nullable = false, insertable = false, updatable = false)
	private Episode episode;

	public GeneratedReportContent() {
	}

	public GeneratedReportContentPK getId() {
		return this.id;
	}

	public void setId(GeneratedReportContentPK id) {
		this.id = id;
	}

	public int getOrderInReport() {
		return this.orderInReport;
	}

	public void setOrderInReport(int orderInReport) {
		this.orderInReport = orderInReport;
	}

	public GeneratedReport getGeneratedReport() {
		return this.generatedReport;
	}

	public void setGeneratedReport(GeneratedReport generatedReport) {
		this.generatedReport = generatedReport;
	}

	public Episode getEpisode() {
		return this.episode;
	}

	public void setEpisode(Episode episode) {
		this.episode = episode;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}
}
