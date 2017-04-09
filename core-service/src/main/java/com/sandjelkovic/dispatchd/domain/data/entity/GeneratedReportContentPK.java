package com.sandjelkovic.dispatchd.domain.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the GeneratedReportContent database table.
 */
@Embeddable
public class GeneratedReportContentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "EpisodeId", insertable = false, updatable = false, unique = true, nullable = false)
	private Long episodeId;

	@Column(name = "GeneratedreportId", insertable = false, updatable = false, unique = true, nullable = false)
	private Long generatedreportId;

	public GeneratedReportContentPK() {
	}

	public Long getEpisodeId() {
		return this.episodeId;
	}

	public void setEpisodeId(Long episodeId) {
		this.episodeId = episodeId;
	}

	public Long getGeneratedreportId() {
		return this.generatedreportId;
	}

	public void setGeneratedreportId(Long generatedreportId) {
		this.generatedreportId = generatedreportId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GeneratedReportContentPK)) {
			return false;
		}
		GeneratedReportContentPK castOther = (GeneratedReportContentPK) other;
		return
				this.episodeId.equals(castOther.episodeId)
						&& this.generatedreportId.equals(castOther.generatedreportId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.episodeId.hashCode();
		hash = hash * prime + this.generatedreportId.hashCode();

		return hash;
	}
}
