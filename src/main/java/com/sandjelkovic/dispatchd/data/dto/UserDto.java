package com.sandjelkovic.dispatchd.data.dto;

import org.hibernate.validator.constraints.Email;

import java.util.HashSet;
import java.util.Set;

public class UserDto {

	private Long id;

	@Email
	private String email;

	private String password;

	private int reportsToKeep = 5;

	private String username;

	private boolean approved = false;

	private Set<String> authorities = new HashSet();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getReportsToKeep() {
		return reportsToKeep;
	}

	public void setReportsToKeep(int reportsToKeep) {
		this.reportsToKeep = reportsToKeep;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<String> authorities) {
		this.authorities = authorities;
	}
}
