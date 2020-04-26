package com.sschakraborty.platform.kjudge.error.errorCode;

public enum SecurityErrorCode implements ErrorCode {
	UNSUPPORTED_ENCODING("Unsupported encoding!"),
	TOKEN_CREATION_FAILED("Token creation failed!"),
	JWT_VERIFICATION_FAILED("JWT Verification Failed!"),
	UNSUPPORTED_CREDENTIALS("Credentials not supported!"),
	INVALID_PRINCIPAL_LOGIN_ATTEMPT("Login principal is invalid!"),
	INVALID_ENCRYPTION_ALGORITHM("Encryption algorithm is invalid!"),
	INVALID_CREDENTIALS("Invalid credentials!");

	private final String message;

	SecurityErrorCode(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}