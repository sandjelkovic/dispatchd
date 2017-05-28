package com.sandjelkovic.dispatchd.domain.data.entity;

import com.sandjelkovic.dispatchd.common.helper.EmptyCollections;

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
 * The persistent class for the TvShow database table.
 */
@Entity
@Table(name = "TvShow")
@NamedQuery(name = "TvShow.findAll", query = "SELECT t FROM TvShow t")
public class TvShow extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Lob
	@Column(nullable = true)
	private String description;

	@Column(nullable = true)
	private Integer year;

	@Column(nullable = false, length = 50)
	private String status;

	@Column(length = 20)
	private String imdbId;

	@Column(length = 20)
	private String tmdbId;

	@Column(length = 50)
	private String traktId;

	@Column(length = 20)
	private String tvdbId;

	@Column(nullable = false)
	private Timestamp lastLocalUpdate;

	//bi-directional many-to-one association to Episode
	@OneToMany(mappedBy = "tvShow")
	private List<Episode> episodes = EmptyCollections.list();

	//bi-directional many-to-one association to Season
	@OneToMany(mappedBy = "tvShow")
	private List<Season> seasons = EmptyCollections.list();

	//bi-directional many-to-one association to TvShowInternalEvent
	@OneToMany(mappedBy = "tvShow")
	private List<TvShowInternalEvent> tvShowInternalEvents = EmptyCollections.list();

	//bi-directional many-to-one association to ReportTemplate2TvShow
	@OneToMany(mappedBy = "tvShow")
	private List<ReportTemplate2TvShow> reportTemplate2TvShows = EmptyCollections.list();

	//uni-directional many-to-one association to ImagesGroup
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "imagesId", nullable = true)
	private ImagesGroup imagesGroup;

	//bi-directional many-to-one association to UserFollowingTvShow
	@OneToMany(mappedBy = "tvShow")
	private List<UserFollowingTvShow> usersFollowing = EmptyCollections.list();

	public TvShow() {
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<Episode> getEpisodes() {
		return this.episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}

	public Episode addEpisode(Episode episode) {
		getEpisodes().add(episode);
		episode.setTvShow(this);

		return episode;
	}

	public Episode removeEpisode(Episode episode) {
		getEpisodes().remove(episode);
		episode.setTvShow(null);

		return episode;
	}

	public List<Season> getSeasons() {
		return this.seasons;
	}

	public void setSeasons(List<Season> seasons) {
		this.seasons = seasons;
	}

	public Season addSeason(Season season) {
		getSeasons().add(season);
		season.setTvShow(this);

		return season;
	}

	public Season removeSeason(Season season) {
		getSeasons().remove(season);
		season.setTvShow(null);

		return season;
	}

	public List<TvShowInternalEvent> getTvShowInternalEvents() {
		return this.tvShowInternalEvents;
	}

	public void setTvShowInternalEvents(List<TvShowInternalEvent> tvShowInternalEvents) {
		this.tvShowInternalEvents = tvShowInternalEvents;
	}

	public TvShowInternalEvent addShowInternalEvent(TvShowInternalEvent tvShowInternalEvent) {
		getTvShowInternalEvents().add(tvShowInternalEvent);
		tvShowInternalEvent.setTvShow(this);

		return tvShowInternalEvent;
	}

	public TvShowInternalEvent removeShowInternalEvent(TvShowInternalEvent tvShowInternalEvent) {
		getTvShowInternalEvents().remove(tvShowInternalEvent);
		tvShowInternalEvent.setTvShow(null);

		return tvShowInternalEvent;
	}

	public List<ReportTemplate2TvShow> getReportTemplate2TvShows() {
		return this.reportTemplate2TvShows;
	}

	public void setReportTemplate2TvShows(List<ReportTemplate2TvShow> reportTemplate2TvShows) {
		this.reportTemplate2TvShows = reportTemplate2TvShows;
	}

	public ReportTemplate2TvShow addShowReportTemplate(ReportTemplate2TvShow reportTemplate2TvShow) {
		getReportTemplate2TvShows().add(reportTemplate2TvShow);
		reportTemplate2TvShow.setTvShow(this);

		return reportTemplate2TvShow;
	}

	public ReportTemplate2TvShow removeShowReportTemplate(ReportTemplate2TvShow reportTemplate2TvShow) {
		getReportTemplate2TvShows().remove(reportTemplate2TvShow);
		reportTemplate2TvShow.setTvShow(null);

		return reportTemplate2TvShow;
	}

	public ImagesGroup getImagesGroup() {
		return this.imagesGroup;
	}

	public void setImagesGroup(ImagesGroup imagesGroup) {
		this.imagesGroup = imagesGroup;
	}

	public List<UserFollowingTvShow> getUsersFollowing() {
		return this.usersFollowing;
	}

	public void setUsersFollowing(List<UserFollowingTvShow> usersFollowing) {
		this.usersFollowing = usersFollowing;
	}

	public UserFollowingTvShow addUsersFollowing(UserFollowingTvShow usersFollowing) {
		getUsersFollowing().add(usersFollowing);
		usersFollowing.setTvShow(this);

		return usersFollowing;
	}

	public UserFollowingTvShow removeUsersFollowing(UserFollowingTvShow usersFollowing) {
		getUsersFollowing().remove(usersFollowing);
		usersFollowing.setTvShow(null);

		return usersFollowing;
	}

	public Timestamp getLastLocalUpdate() {
		return lastLocalUpdate;
	}

	public void setLastLocalUpdate(Timestamp lastLocalUpdate) {
		this.lastLocalUpdate = lastLocalUpdate;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}

	public enum Status {
		NOTSTARTED,
		ONGOING,
		ONBREAK,
		FINISHED;
	}
}
