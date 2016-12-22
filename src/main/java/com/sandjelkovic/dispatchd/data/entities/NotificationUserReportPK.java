package com.sandjelkovic.dispatchd.data.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the NotificationUserReport database table.
 */
@Embeddable
public class NotificationUserReportPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "UserId", insertable = false, updatable = false, unique = true, nullable = false)
	private Long userId;

	@Column(name = "GeneratedreportId", insertable = false, updatable = false, unique = true, nullable = false)
	private Long generatedreportId;

	public NotificationUserReportPK() {
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
		if (!(other instanceof NotificationUserReportPK)) {
			return false;
		}
		NotificationUserReportPK castOther = (NotificationUserReportPK) other;
		return
				this.userId.equals(castOther.userId)
						&& this.generatedreportId.equals(castOther.generatedreportId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.generatedreportId.hashCode();

		return hash;
	}
}
