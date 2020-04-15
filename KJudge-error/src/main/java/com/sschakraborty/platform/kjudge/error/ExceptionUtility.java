package com.sschakraborty.platform.kjudge.error;

import com.sschakraborty.platform.kjudge.error.errorCode.ErrorCode;

public class ExceptionUtility {
	public static void throwGenericException(ErrorCode errorCode, String message) throws GenericException {
		throw new GenericException(errorCode, message);
	}
}