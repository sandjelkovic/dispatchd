package com.sandjelkovic.dispatchd.domain.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the ReportTemplate2TvShow database table.
 */
@Embeddable
public class ReportTemplate2TvShowPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "TvShowId", insertable = false, updatable = false, unique = true, nullable = false)
	private Long showId;

	@Column(name = "ReporttemplateId", insertable = false, updatable = false, unique = true, nullable = false)
	private Long reporttemplateId;

	public ReportTemplate2TvShowPK() {
	}

	public Long getShowId() {
		return this.showId;
	}

	public void setShowId(Long showId) {
		this.showId = showId;
	}

	public Long getReporttemplateId() {
		return this.reporttemplateId;
	}

	public void setReporttemplateId(Long reporttemplateId) {
		this.reporttemplateId = reporttemplateId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ReportTemplate2TvShowPK)) {
			return false;
		}
		ReportTemplate2TvShowPK castOther = (ReportTemplate2TvShowPK) other;
		return
				this.showId.equals(castOther.showId)
						&& this.reporttemplateId.equals(castOther.reporttemplateId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.showId.hashCode();
		hash = hash * prime + this.reporttemplateId.hashCode();

		return hash;
	}
}
