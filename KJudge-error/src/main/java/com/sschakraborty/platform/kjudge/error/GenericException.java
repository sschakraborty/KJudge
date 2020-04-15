package com.sschakraborty.platform.kjudge.error;

import com.sschakraborty.platform.kjudge.error.errorCode.ErrorCode;

public class GenericException extends AbstractBusinessException {
	public GenericException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}
}