package com.sandjelkovic.dispatchd.data.entities;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the PassResetToken database table.
 */
@Entity
@Table(name = "PassResetToken")
@NamedQuery(name = "PassResetToken.findAll", query = "SELECT p FROM PassResetToken p")
public class PassResetToken extends BasicEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Long id;

	@Column(nullable = false, length = 255)
	private String token;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	public PassResetToken() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	protected Object getInternalId() {
		return getId();
	}
}
