package com.sandjelkovic.dispatchd.data.entities;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the NotificationUserReport database table.
 */
@Entity
@Table(name = "NotificationUserReport")
@NamedQuery(name = "NotificationUserReport.findAll", query = "SELECT n FROM NotificationUserReport n")
public class NotificationUserReport extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NotificationUserReportPK id;

	@Column(nullable = false)
	private boolean alreadyRead;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UserId", nullable = false, insertable = false, updatable = false)
	private User user;

	//uni-directional many-to-one association to GeneratedReport
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GeneratedreportId", nullable = false, insertable = false, updatable = false)
	private GeneratedReport generatedReport;

	public NotificationUserReport() {
	}

	public NotificationUserReportPK getId() {
		return this.id;
	}

	public void setId(NotificationUserReportPK id) {
		this.id = id;
	}

	public boolean getAlreadyRead() {
		return this.alreadyRead;
	}

	public void setAlreadyRead(boolean alreadyRead) {
		this.alreadyRead = alreadyRead;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public GeneratedReport getGeneratedReport() {
		return this.generatedReport;
	}

	public void setGeneratedReport(GeneratedReport generatedReport) {
		this.generatedReport = generatedReport;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}
}
