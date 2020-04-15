package com.sschakraborty.platform.kjudge.error;

import com.sschakraborty.platform.kjudge.error.errorCode.ErrorCode;

public abstract class AbstractBusinessException extends Exception {
	private ErrorCode errorCode;

	public AbstractBusinessException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return this.errorCode;
	}

	@Override
	public String toString() {
		return ExceptionMessageBuilder.buildMessage(this);
	}
}
