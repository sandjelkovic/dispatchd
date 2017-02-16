package com.sandjelkovic.dispatchd.domain.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the Person database table.
 */
@Entity
@Table(name = "Person")
@NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
public class Person extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = 20)
	private String imdbId;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(length = 20)
	private String tmdbId;

	@Column(length = 50)
	private String traktId;

	@Column(length = 20)
	private String tvdbId;

	public Person() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImdbId() {
		return this.imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTmdbId() {
		return this.tmdbId;
	}

	public void setTmdbId(String tmdbId) {
		this.tmdbId = tmdbId;
	}

	public String getTraktId() {
		return this.traktId;
	}

	public void setTraktId(String traktId) {
		this.traktId = traktId;
	}

	public String getTvdbId() {
		return this.tvdbId;
	}

	public void setTvdbId(String tvdbId) {
		this.tvdbId = tvdbId;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}
}
