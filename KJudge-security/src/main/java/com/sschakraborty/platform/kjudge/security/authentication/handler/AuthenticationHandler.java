package com.sschakraborty.platform.kjudge.security.authentication.handler;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.security.credential.Credential;

public interface AuthenticationHandler {
	void authenticate(Credential credential) throws AbstractBusinessException;

	boolean supports(Credential credential);
}