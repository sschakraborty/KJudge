package com.sschakraborty.platform.kjudge.error.errorCode;

public enum StandardErrorCode implements ErrorCode {
	INITIALIZATION_ERROR("Initialization failed!");

	private final String message;

	StandardErrorCode(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}