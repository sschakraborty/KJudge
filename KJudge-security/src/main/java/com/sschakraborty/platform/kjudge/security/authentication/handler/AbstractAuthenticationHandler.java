package com.sschakraborty.platform.kjudge.security.authentication.handler;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.SecurityErrorCode;
import com.sschakraborty.platform.kjudge.security.authentication.utility.AuthenticationPrincipalResolver;
import com.sschakraborty.platform.kjudge.security.credential.Credential;

public abstract class AbstractAuthenticationHandler implements AuthenticationHandler {
	private final AuthenticationPrincipalResolver authenticationPrincipalResolver;

	public AbstractAuthenticationHandler(AuthenticationPrincipalResolver resolver) {
		this.authenticationPrincipalResolver = resolver;
	}

	@Override
	public void authenticate(Credential credential) throws AbstractBusinessException {
		if (!supports(credential)) {
			ExceptionUtility.throwGenericException(
				SecurityErrorCode.UNSUPPORTED_CREDENTIALS,
				String.format(
					"Credential %s cannot be used for handler %s!",
					credential.getClass().getSimpleName(),
					this.getClass().getSimpleName()
				)
			);
		}
		this.performAuthentication(
			this.authenticationPrincipalResolver.resolvePrincipal(
				credential.getPrincipalType(),
				credential.getPrincipal()
			),
			credential
		);
	}

	abstract <T> void performAuthentication(T principalObject, Credential credential) throws AbstractBusinessException;
}