package com.sandjelkovic.dispatchd.data.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the TvShowInternalEvent database table.
 */
@Entity
@Table(name = "TvShowInternalEvent")
@NamedQuery(name = "TvShowInternalEvent.findAll", query = "SELECT s FROM TvShowInternalEvent s")
public class TvShowInternalEvent extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(nullable = false)
	private Timestamp datetime;

	@Column(nullable = false, length = 50)
	private String typeOfEvent;

	//bi-directional many-to-one association to TvShow
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tvShowId", nullable = false)
	private TvShow tvShow;

	public TvShowInternalEvent() {
	}

	public Long getId() {
		return this.id;
	}

	public void Id(Long id) {
		this.id = id;
	}

	public Timestamp getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}

	public String getTypeOfEvent() {
		return this.typeOfEvent;
	}

	public void setTypeOfEvent(String typeOfEvent) {
		this.typeOfEvent = typeOfEvent;
	}

	public TvShow getTvShow() {
		return this.tvShow;
	}

	public void setTvShow(TvShow tvShow) {
		this.tvShow = tvShow;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}
}
