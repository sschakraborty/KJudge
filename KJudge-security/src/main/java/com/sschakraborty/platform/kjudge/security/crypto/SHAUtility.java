package com.sschakraborty.platform.kjudge.security.crypto;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.SecurityErrorCode;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAUtility {
	private static byte[] getSHA(final String input) throws AbstractBusinessException {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			ExceptionUtility.throwGenericException(
				SecurityErrorCode.INVALID_ENCRYPTION_ALGORITHM,
				String.format(
					"Encryption algorithm (%s) not supported!",
					"SHA-256"
				)
			);
		}
		return digest.digest(input.getBytes(StandardCharsets.UTF_8));
	}

	private static String toHexString(final byte[] hash) {
		final BigInteger number = new BigInteger(1, hash);
		StringBuilder hexString = new StringBuilder(number.toString(16));
		while (hexString.length() < 32) {
			hexString.insert(0, '0');
		}
		return hexString.toString();
	}

	public static String sha256(String payload) throws AbstractBusinessException {
		return toHexString(getSHA(payload));
	}
}