package com.sandjelkovic.dispatchd.data.entities;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the Movie database table.
 */
@Entity
@Table(name = "Movie")
@NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m")
public class Movie extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(length = 20)
	private String imdbId;

	@Column(nullable = false, length = 255)
	private String title;

	@Column(length = 20)
	private String tmdbId;

	@Column(length = 50)
	private String traktId;

	@Column(length = 20)
	private String tvdbId;

	@Column(nullable = false)
	private int year;

	public Movie() {
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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}
}
