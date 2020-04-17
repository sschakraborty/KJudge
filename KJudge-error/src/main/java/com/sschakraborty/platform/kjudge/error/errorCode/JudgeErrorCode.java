package com.sschakraborty.platform.kjudge.error.errorCode;

public enum JudgeErrorCode implements ErrorCode {
	UNSUPPORTED_LANGUAGE("Unsupported Language!");

	private final String message;

	JudgeErrorCode(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}