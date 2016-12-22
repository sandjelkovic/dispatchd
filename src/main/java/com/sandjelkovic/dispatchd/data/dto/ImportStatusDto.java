package com.sandjelkovic.dispatchd.data.dto;

import com.sandjelkovic.dispatchd.data.entities.ImportProgressStatus;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

public class ImportStatusDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	@URL
	@Length(max = 500)
	private String mediaUrl;

	@NotNull
	private ZonedDateTime initiationTime;

	private ZonedDateTime finishTime;

	@Length(max = 50)
	private ImportProgressStatus status;

	@URL
	@Length(max = 500)
	private String redirectUrl;

	public ImportStatusDto() {
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

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

}
