package com.sandjelkovic.dispatchd.domain.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table
public class UpdateJob extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(nullable = false)
	@NotNull
	private ZonedDateTime finishTime;

	@Column
	@NotNull
	private boolean success;

	public UpdateJob() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ZonedDateTime getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(ZonedDateTime finishTime) {
		this.finishTime = finishTime;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}

	public UpdateJob id(Long id) {
		this.id = id;
		return this;
	}

	public UpdateJob finishTime(ZonedDateTime finishTime) {
		this.finishTime = finishTime;
		return this;
	}

	public UpdateJob success(boolean success) {
		this.success = success;
		return this;
	}
}
