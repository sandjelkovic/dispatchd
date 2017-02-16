package com.sandjelkovic.dispatchd.domain.data.entity;

import com.sandjelkovic.dispatchd.helper.EmptyCollections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Season database table.
 */
@Entity
@Table(name = "Season")
@NamedQuery(name = "Season.findAll", query = "SELECT s FROM Season s")
public class Season extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Lob
	@Column(nullable = true)
	private String description;

	@Column(length = 20)
	private String imdbId;

	@Column(nullable = false)
	private String number;

	@Column(length = 20)
	private String tmdbId;

	@Column(length = 50)
	private String traktId;

	@Column(length = 20)
	private String tvdbId;

	@Column(nullable = true)
	private Integer episodesAiredCount;

	@Column(nullable = true)
	private Timestamp airdate;

	@Column(nullable = true)
	private Integer episodesCount;

	//bi-directional many-to-one association to Episode
	@OneToMany(mappedBy = "season")
	private List<Episode> episodes = EmptyCollections.list();

	//uni-directional many-to-one association to ImagesGroup
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "imagesId", nullable = true)
	private ImagesGroup imagesGroup;

	//bi-directional many-to-one association to TvShow
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TvShowId", nullable = false)
	private TvShow tvShow;

	public Season() {
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

	public String getImdbId() {
		return this.imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public List<Episode> getEpisodes() {
		return this.episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}

	public Episode addEpisode(Episode episode) {
		getEpisodes().add(episode);
		episode.setSeason(this);

		return episode;
	}

	public Episode removeEpisode(Episode episode) {
		getEpisodes().remove(episode);
		episode.setSeason(null);

		return episode;
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

	public Integer getEpisodesAiredCount() {
		return episodesAiredCount;
	}

	public void setEpisodesAiredCount(Integer episodesAiredCount) {
		this.episodesAiredCount = episodesAiredCount;
	}

	public Timestamp getAirdate() {
		return airdate;
	}

	public void setAirdate(Timestamp airdate) {
		this.airdate = airdate;
	}

	public Integer getEpisodesCount() {
		return episodesCount;
	}

	public void setEpisodesCount(Integer episodesCount) {
		this.episodesCount = episodesCount;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}
}
