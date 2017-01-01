package com.sandjelkovic.dispatchd.data.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "Episode")
@NamedQuery(name = "Episode.findAll", query = "SELECT e FROM Episode e")
public class Episode extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(nullable = true)
	private ZonedDateTime airdate;

	@Column(nullable = false)
	private ZonedDateTime lastUpdated;

	@Column(nullable = true, length = 50)
	private String customNumbering;

	@Lob()
	@Column(nullable = true)
	private String description;

	@Column(length = 20)
	private String imdbId;

	@Column(nullable = false)
	private Integer number;

	@Column(nullable = false)
	private String seasonNumber;

	@Column(nullable = true)
	private String title;

	@Column(length = 20)
	private String tmdbId;

	@Column(length = 50)
	private String traktId;

	@Column(length = 20)
	private String tvdbId;

	//bi-directional many-to-one association to Season
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seasonId")
	private Season season;

	//uni-directional many-to-one association to ImagesGroup
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "imagesId", nullable = true)
	private ImagesGroup imagesGroup;

	//bi-directional many-to-one association to TvShow
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "showId", nullable = false)
	private TvShow tvShow;

	//bi-directional many-to-many association to User
	@ManyToMany
	@JoinTable(name = "UserWatchedEpisode",
			joinColumns = {
					@JoinColumn(name = "Episode", nullable = false)},
			inverseJoinColumns = {
					@JoinColumn(name = "User", nullable = false)}
	)
	private List<User> users;

	public Episode() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ZonedDateTime getAirdate() {
		return this.airdate;
	}

	public void setAirdate(ZonedDateTime airdate) {
		this.airdate = airdate;
	}

	public ZonedDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(ZonedDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getCustomNumbering() {
		return this.customNumbering;
	}

	public void setCustomNumbering(String customNumbering) {
		this.customNumbering = customNumbering;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImdbId() {
		return this.imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getSeasonNumber() {
		return seasonNumber;
	}

	public void setSeasonNumber(String seasonNumber) {
		this.seasonNumber = seasonNumber;
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

	public Season getSeason() {
		return this.season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public ImagesGroup getImagesGroup() {
		return this.imagesGroup;
	}

	public void setImagesGroup(ImagesGroup imagesGroup) {
		this.imagesGroup = imagesGroup;
	}

	public TvShow getTvShow() {
		return this.tvShow;
	}

	public void setTvShow(TvShow tvShow) {
		this.tvShow = tvShow;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	protected Object getInternalId() {
		return null;
	}

}
