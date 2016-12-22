package com.sandjelkovic.dispatchd.data.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;


/**
 * The persistent class for the UserFollowingTvShow database table.
 */
@Entity
@Table(name = "UserFollowingTvShow")
@NamedQuery(name = "UserFollowingTvShow.findAll", query = "SELECT u FROM UserFollowingTvShow u")
public class UserFollowingTvShow extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserFollowingTvShowPK id;

	@Column(nullable = false)
	private boolean notify = false;

	@Column(nullable = false)
	private BigInteger userPickedRelativeTimeToNotify = new BigInteger("0");

	//bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UserId", nullable = false, insertable = false, updatable = false)
	private User user;

	//bi-directional many-to-one association to TvShow
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TvShowId", nullable = false, insertable = false, updatable = false)
	private TvShow tvShow;

	public UserFollowingTvShow() {
	}

	public UserFollowingTvShow(User user, TvShow tvShow) {
		this.user = user;
		this.tvShow = tvShow;
		this.id = new UserFollowingTvShowPK(user, tvShow);
	}

	public UserFollowingTvShowPK getId() {
		return this.id;
	}

	public void setId(UserFollowingTvShowPK id) {
		this.id = id;
	}

	public boolean getNotify() {
		return this.notify;
	}

	public void setNotify(boolean notify) {
		this.notify = notify;
	}

	public BigInteger getUserPickedRelativeTimeToNotify() {
		return this.userPickedRelativeTimeToNotify;
	}

	public void setUserPickedRelativeTimeToNotify(BigInteger userPickedRelativeTimeToNotify) {
		this.userPickedRelativeTimeToNotify = userPickedRelativeTimeToNotify;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
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
