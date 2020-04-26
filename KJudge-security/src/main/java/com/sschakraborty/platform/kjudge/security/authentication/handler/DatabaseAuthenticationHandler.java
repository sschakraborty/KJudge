package com.sschakraborty.platform.kjudge.security.authentication.handler;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.SecurityErrorCode;
import com.sschakraborty.platform.kjudge.security.authentication.utility.AuthenticationPrincipalResolver;
import com.sschakraborty.platform.kjudge.security.credential.Credential;
import com.sschakraborty.platform.kjudge.security.credential.DatabaseAuthenticationCredential;
import com.sschakraborty.platform.kjudge.security.crypto.SHAUtility;
import com.sschakraborty.platform.kjudge.shared.model.User;

public class DatabaseAuthenticationHandler extends AbstractAuthenticationHandler {
	public DatabaseAuthenticationHandler(AuthenticationPrincipalResolver resolver) {
		super(resolver);
	}

	@Override
	public boolean supports(Credential credential) {
		return credential instanceof DatabaseAuthenticationCredential;
	}

	@Override
	<T> void performAuthentication(T principalObject, Credential credential) throws AbstractBusinessException {
		final DatabaseAuthenticationCredential authenticationCredential = (DatabaseAuthenticationCredential) credential;
		final User principal = (User) principalObject;

		if (principal == null) {
			ExceptionUtility.throwGenericException(
				SecurityErrorCode.INVALID_PRINCIPAL_LOGIN_ATTEMPT,
				String.format(
					"Principal (%s) is invalid!",
					credential.getPrincipal()
				)
			);
		}

		final String encryptedPassword = SHAUtility.sha256(authenticationCredential.getPassword());
		if (!encryptedPassword.equals(principal.getPassword())) {
			ExceptionUtility.throwGenericException(
				SecurityErrorCode.INVALID_CREDENTIALS,
				"Password provided is invalid!"
			);
		}
	}
}