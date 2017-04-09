package com.sandjelkovic.dispatchd.domain.data.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;


@Entity
@Table(name = "ImportStatus")
@NamedQuery(name = "ImportStatus.findAll", query = "SELECT s FROM ImportStatus s")
public class ImportStatus extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(nullable = true, length = 500)
	//@URL
	@Length(max = 1000)
	private String mediaUrl;

	@Column(nullable = false)
	@NotNull
	private ZonedDateTime initiationTime;

	@Column(nullable = true)
	private ZonedDateTime finishTime;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ImportProgressStatus status;

	public ImportStatus() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public ZonedDateTime getInitiationTime() {
		return initiationTime;
	}

	public void setInitiationTime(ZonedDateTime initiationTime) {
		this.initiationTime = initiationTime;
	}

	public ZonedDateTime getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(ZonedDateTime finishTime) {
		this.finishTime = finishTime;
	}

	public ImportProgressStatus getStatus() {
		return status;
	}

	public void setStatus(ImportProgressStatus status) {
		this.status = status;
	}

	public ImportStatus id(final Long id) {
		this.id = id;
		return this;
	}

	public ImportStatus mediaUrl(final String mediaUrl) {
		this.mediaUrl = mediaUrl;
		return this;
	}

	public ImportStatus initiationTime(final ZonedDateTime initiationTime) {
		this.initiationTime = initiationTime;
		return this;
	}

	public ImportStatus finishTime(final ZonedDateTime finishTime) {
		this.finishTime = finishTime;
		return this;
	}

	public ImportStatus status(final ImportProgressStatus status) {
		this.status = status;
		return this;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}
}
