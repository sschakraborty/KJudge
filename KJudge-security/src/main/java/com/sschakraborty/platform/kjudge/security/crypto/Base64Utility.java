package com.sschakraborty.platform.kjudge.security.crypto;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.GenericException;
import com.sschakraborty.platform.kjudge.error.errorCode.SecurityErrorCode;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Utility {
	private Base64Utility() {
	}

	public static String base64UrlEncode(String payload) throws AbstractBusinessException {
		final byte[] bytes;
		try {
			bytes = payload.getBytes("UTF-8");
			return base64UrlEncode(bytes);
		} catch (UnsupportedEncodingException e) {
			ExceptionUtility.throwGenericException(
				SecurityErrorCode.UNSUPPORTED_ENCODING,
				"UTF-8 encoding is not supported!"
			);
		}
		return null;
	}

	public static String base64UrlEncode(byte[] bytes) {
		return Base64.getUrlEncoder().encodeToString(bytes);
	}

	public static String base64UrlDecode(String payload) throws GenericException {
		try {
			return new String(Base64.getUrlDecoder().decode(payload), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			ExceptionUtility.throwGenericException(
				SecurityErrorCode.UNSUPPORTED_ENCODING,
				"UTF-8 encoding is not supported!"
			);
		}
		return null;
	}
}