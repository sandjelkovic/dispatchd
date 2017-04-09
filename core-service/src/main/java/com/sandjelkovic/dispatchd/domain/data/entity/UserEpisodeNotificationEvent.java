package com.sandjelkovic.dispatchd.domain.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the UserEpisodeNotificationEvent database table.
 */
@Entity
@Table(name = "UserEpisodeNotificationEvent")
@NamedQuery(name = "UserEpisodeNotificationEvent.findAll", query = "SELECT u FROM UserEpisodeNotificationEvent u")
public class UserEpisodeNotificationEvent extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(nullable = false, length = 250)
	private String description;

	@Column(nullable = false)
	private Timestamp notifyTime;

	@Column(nullable = false)
	private boolean notified;

	@Column(nullable = false, length = 100)
	private String title;

	//uni-directional many-to-one association to Episode
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "episodeId", nullable = false)
	private Episode episode;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	public UserEpisodeNotificationEvent() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getNotifyTime() {
		return this.notifyTime;
	}

	public void setNotifyTime(Timestamp notifyTime) {
		this.notifyTime = notifyTime;
	}

	public boolean getNotified() {
		return this.notified;
	}

	public void setNotified(boolean notified) {
		this.notified = notified;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Episode getEpisode() {
		return this.episode;
	}

	public void setEpisode(Episode episode) {
		this.episode = episode;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}

	@Override
	public String toString() {
		return "UserEpisodeNotificationEvent{" +
				"id=" + id +
				", description='" + description + '\'' +
				", notifyTime=" + notifyTime +
				", notified=" + notified +
				", title='" + title + '\'' +
				", episode=" + episode +
				", user=" + user +
				'}';
	}
}
