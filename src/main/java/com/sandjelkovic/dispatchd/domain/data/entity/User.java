package com.sandjelkovic.dispatchd.domain.data.entity;

import com.sandjelkovic.dispatchd.helper.EmptyCollections;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the User database table.
 */
@Entity
@Table(name = "User")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(unique = true, nullable = false, length = 70)
	@Email
	private String email;

	@Column(nullable = false, length = 256)
	private String passw;

	@Transient
	private String passwordPlain;

	@Column(nullable = false)
	private int reportsToKeep = 5;

	@Column(unique = true, nullable = false, length = 50)
	private String username;

	@Column(nullable = false)
	private boolean enabled = true;

	@Column(nullable = false)
	private boolean approved = false;

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> authorities = EmptyCollections.set();

	//bi-directional many-to-one association to NotificationUserReport
	@OneToMany(mappedBy = "user")
	private List<NotificationUserReport> notificationUserReports = EmptyCollections.list();

	//bi-directional many-to-one association to PassResetToken
	@OneToMany(mappedBy = "user")
	private List<PassResetToken> passResetTokens = EmptyCollections.list();

	//bi-directional many-to-one association to ReportTemplate
	@OneToMany(mappedBy = "user")
	private List<ReportTemplate> reportTemplates = EmptyCollections.list();

	//bi-directional many-to-one association to UserEpisodeNotificationEvent
	@OneToMany(mappedBy = "user")
	private List<UserEpisodeNotificationEvent> episodeNotifications = EmptyCollections.list();

	//bi-directional many-to-one association to UserFollowingTvShow
	@OneToMany(mappedBy = "user")
	private List<UserFollowingTvShow> followingShows = EmptyCollections.list();

	//bi-directional many-to-many association to Episode
	@ManyToMany(mappedBy = "users")
	private List<Episode> episodes = EmptyCollections.list();

	public User() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getApproved() {
		return this.approved;
	}

	public String getPassw() {
		return this.passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}

	public int getReportsToKeep() {
		return this.reportsToKeep;
	}

	public void setReportsToKeep(int reportsToKeep) {
		this.reportsToKeep = reportsToKeep;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public String getPasswordPlain() {
		return passwordPlain;
	}

	public void setPasswordPlain(String passwordPlain) {
		this.passwordPlain = passwordPlain;
	}

	public List<NotificationUserReport> getNotificationUserReports() {
		return this.notificationUserReports;
	}

	public void setNotificationUserReports(List<NotificationUserReport> notificationUserReports) {
		this.notificationUserReports = notificationUserReports;
	}

	public NotificationUserReport addNotificationUserReport(NotificationUserReport notificationUserReport) {
		getNotificationUserReports().add(notificationUserReport);
		notificationUserReport.setUser(this);

		return notificationUserReport;
	}

	public NotificationUserReport removeNotificationUserReport(NotificationUserReport notificationUserReport) {
		getNotificationUserReports().remove(notificationUserReport);
		notificationUserReport.setUser(null);

		return notificationUserReport;
	}

	public List<PassResetToken> getPassResetTokens() {
		return this.passResetTokens;
	}

	public void setPassResetTokens(List<PassResetToken> passResetTokens) {
		this.passResetTokens = passResetTokens;
	}

	public PassResetToken addPassResetToken(PassResetToken passResetToken) {
		getPassResetTokens().add(passResetToken);
		passResetToken.setUser(this);

		return passResetToken;
	}

	public PassResetToken removePassResetToken(PassResetToken passResetToken) {
		getPassResetTokens().remove(passResetToken);
		passResetToken.setUser(null);

		return passResetToken;
	}

	public List<ReportTemplate> getReportTemplates() {
		return this.reportTemplates;
	}

	public void setReportTemplates(List<ReportTemplate> reportTemplates) {
		this.reportTemplates = reportTemplates;
	}

	public ReportTemplate addReportTemplate(ReportTemplate reportTemplate) {
		getReportTemplates().add(reportTemplate);
		reportTemplate.setUser(this);

		return reportTemplate;
	}

	public ReportTemplate removeReportTemplate(ReportTemplate reportTemplate) {
		getReportTemplates().remove(reportTemplate);
		reportTemplate.setUser(null);

		return reportTemplate;
	}

	public List<UserEpisodeNotificationEvent> getEpisodeNotifications() {
		return this.episodeNotifications;
	}

	public void setEpisodeNotifications(List<UserEpisodeNotificationEvent> episodeNotifications) {
		this.episodeNotifications = episodeNotifications;
	}

	public UserEpisodeNotificationEvent addEpisodeNotification(UserEpisodeNotificationEvent episodeNotification) {
		getEpisodeNotifications().add(episodeNotification);
		episodeNotification.setUser(this);

		return episodeNotification;
	}

	public UserEpisodeNotificationEvent removeEpisodeNotification(UserEpisodeNotificationEvent episodeNotification) {
		getEpisodeNotifications().remove(episodeNotification);
		episodeNotification.setUser(null);

		return episodeNotification;
	}

	public List<UserFollowingTvShow> getFollowingShows() {
		return this.followingShows;
	}

	public void setFollowingShows(List<UserFollowingTvShow> followingShows) {
		this.followingShows = followingShows;
	}

	public UserFollowingTvShow addFollowingShow(UserFollowingTvShow followingShow) {
		getFollowingShows().add(followingShow);
		followingShow.setUser(this);

		return followingShow;
	}

	public UserFollowingTvShow removeFollowingShow(UserFollowingTvShow followingShow) {
		getFollowingShows().remove(followingShow);
		followingShow.setUser(null);

		return followingShow;
	}

	public List<Episode> getEpisodes() {
		return this.episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public User id(final Long id) {
		this.id = id;
		return this;
	}

	public User email(final String email) {
		this.email = email;
		return this;
	}

	public User passw(final String passw) {
		this.passw = passw;
		return this;
	}

	public User passwordPlain(final String passwordPlain) {
		this.passwordPlain = passwordPlain;
		return this;
	}

	public User reportsToKeep(final int reportsToKeep) {
		this.reportsToKeep = reportsToKeep;
		return this;
	}

	public User username(final String username) {
		this.username = username;
		return this;
	}

	public User enabled(final boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public User approved(final boolean approved) {
		this.approved = approved;
		return this;
	}

	public User authorities(final Set<String> authorities) {
		this.authorities = authorities;
		return this;
	}

	public User notificationUserReports(final List<NotificationUserReport> notificationUserReports) {
		this.notificationUserReports = notificationUserReports;
		return this;
	}

	public User passResetTokens(final List<PassResetToken> passResetTokens) {
		this.passResetTokens = passResetTokens;
		return this;
	}

	public User reportTemplates(final List<ReportTemplate> reportTemplates) {
		this.reportTemplates = reportTemplates;
		return this;
	}

	public User episodeNotifications(final List<UserEpisodeNotificationEvent> episodeNotifications) {
		this.episodeNotifications = episodeNotifications;
		return this;
	}

	public User followingShows(final List<UserFollowingTvShow> followingShows) {
		this.followingShows = followingShows;
		return this;
	}

	public User episodes(final List<Episode> episodes) {
		this.episodes = episodes;
		return this;
	}


	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", email='" + email + '\'' +
				", passw='" + passw + '\'' +
				", reportsToKeep=" + reportsToKeep +
				", username='" + username + '\'' +
				", enabled=" + enabled +
				", approved=" + approved +
				", authorities=" + authorities +
				'}';
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}
}
