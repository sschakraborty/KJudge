package com.sschakraborty.platform.kjudge.shared.model;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class User {
	@Id
	@Column(
		name = "PRINCIPAL",
		length = 25
	)
	private String principal;

	@Column(
		name = "PASSWORD",
		nullable = false,
		length = 64
	)
	private String password;

	@JoinColumn(
		name = "PRINCIPAL",
		nullable = false
	)
	@OneToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.EAGER
	)
	@MapsId
	private UserProfile userProfile;

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
}
