package com.sschakraborty.platform.kjudge.security.authentication;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.security.authentication.handler.AuthenticationHandler;
import com.sschakraborty.platform.kjudge.security.authentication.handler.DatabaseAuthenticationHandler;
import com.sschakraborty.platform.kjudge.security.authentication.utility.AuthenticationPrincipalResolver;
import com.sschakraborty.platform.kjudge.security.credential.Credential;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationManager {
	private List<AuthenticationHandler> handlerList;

	public AuthenticationManager(AuthenticationPrincipalResolver resolver) {
		this.handlerList = new ArrayList<>(10);
		this.handlerList.add(
			new DatabaseAuthenticationHandler(resolver)
		);
	}

	public void authenticate(final Credential credential) throws AbstractBusinessException {
		for (final AuthenticationHandler handler : this.handlerList) {
			if (handler.supports(credential)) {
				handler.authenticate(credential);
			}
		}
	}
}