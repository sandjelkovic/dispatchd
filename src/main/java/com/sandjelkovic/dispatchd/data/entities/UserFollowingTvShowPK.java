package com.sandjelkovic.dispatchd.data.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the UserFollowingTvShow database table.
 */
@Embeddable
public class UserFollowingTvShowPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "UserId", insertable = false, updatable = false, unique = true, nullable = false)
	private Long userId;

	@Column(name = "TvShowId", insertable = false, updatable = false, unique = true, nullable = false)
	private Long showId;

	public UserFollowingTvShowPK() {
	}

	public UserFollowingTvShowPK(User user, TvShow tvShow) {
		this(user.getId(), tvShow.getId());
	}

	public UserFollowingTvShowPK(Long userId, Long showId) {
		this.userId = userId;
		this.showId = showId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getShowId() {
		return this.showId;
	}

	public void setShowId(Long showId) {
		this.showId = showId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserFollowingTvShowPK)) {
			return false;
		}
		UserFollowingTvShowPK castOther = (UserFollowingTvShowPK) other;
		return
				this.userId.equals(castOther.userId)
						&& this.showId.equals(castOther.showId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.showId.hashCode();

		return hash;
	}
}
