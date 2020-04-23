package com.sschakraborty.platform.kjudge.error;

import com.sschakraborty.platform.kjudge.error.errorCode.ErrorCode;

public abstract class AbstractBusinessException extends Exception {
	private ErrorCode errorCode;
	private String errorDump;

	public AbstractBusinessException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
		this.errorDump = "";
	}

	public ErrorCode getErrorCode() {
		return this.errorCode;
	}

	@Override
	public String toString() {
		return ExceptionMessageBuilder.buildMessage(this);
	}

	public String getErrorDump() {
		return errorDump;
	}

	public void setErrorDump(String errorDump) {
		if (errorDump != null) {
			this.errorDump = errorDump;
		} else {
			this.errorDump = "";
		}
	}
}