package com.sschakraborty.platform.kjudge.security.credential;

import com.sschakraborty.platform.kjudge.shared.model.User;

public class DatabaseAuthenticationCredential implements Credential {
	private String userPrincipal;
	private String password;

	public void setUserPrincipal(String userPrincipal) {
		this.userPrincipal = userPrincipal;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPrincipal() {
		return this.userPrincipal;
	}

	@Override
	public Class<?> getPrincipalType() {
		return User.class;
	}
}