package com.sschakraborty.platform.kjudge.shared.model;

import com.sschakraborty.platform.kjudge.error.errorCode.ErrorCode;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;

public enum OutputCode {
	COMPILATION_ERROR(JudgeErrorCode.COMPILATION_ERROR),
	RUNTIME_ERROR(JudgeErrorCode.RUNTIME_ERROR),
	WRONG_ANSWER(JudgeErrorCode.WRONG_ANSWER_ERROR),
	TIME_LIMIT_EXCEEDED(JudgeErrorCode.RUN_TIME_LIMIT_EXCEEDED),
	INTERNAL_ERROR(null),
	ACCEPTED(null);

	private final ErrorCode errorCode;

	OutputCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public static OutputCode mapOutputCode(ErrorCode errorCode) {
		for (OutputCode code : OutputCode.values()) {
			if (code.getErrorCode() != null && code.getErrorCode() == errorCode) {
				return code;
			}
		}
		return OutputCode.INTERNAL_ERROR;
	}

	public ErrorCode getErrorCode() {
		return this.errorCode;
	}
}