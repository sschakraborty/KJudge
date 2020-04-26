package com.sschakraborty.platform.kjudge.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.SecurityErrorCode;
import com.sschakraborty.platform.kjudge.security.AccessToken;
import com.sschakraborty.platform.kjudge.security.crypto.SecurityConstant;
import io.vertx.core.json.JsonObject;

public class JWTUtility {
	private static final Algorithm ALGORITHM;
	private static final JWTVerifier JWT_VERIFIER;

	static {
		ALGORITHM = Algorithm.HMAC384(SecurityConstant.SHA_SECRET_KEY);
		JWT_VERIFIER = JWT.require(ALGORITHM)
			.withIssuer(SecurityConstant.ISSUER)
			.withSubject(SecurityConstant.SUBJECT)
			.build();
	}

	public static String encodeIntoJWTString(final AccessToken accessToken) throws AbstractBusinessException {
		try {
			return JWT.create().withIssuer(SecurityConstant.ISSUER)
				.withSubject(SecurityConstant.SUBJECT)
				.withExpiresAt(accessToken.getExpiryDate())
				.withClaim("TOKEN", JsonObject.mapFrom(accessToken).encode())
				.sign(ALGORITHM);
		} catch (Exception e) {
			ExceptionUtility.throwGenericException(
				SecurityErrorCode.TOKEN_CREATION_FAILED,
				String.format(
					"Creation of access token failed: %s",
					e.getMessage()
				)
			);
		}
		return null;
	}

	public static AccessToken decodeAccessToken(final String jwtString) throws AbstractBusinessException {
		try {
			final DecodedJWT decodedJWT = JWT_VERIFIER.verify(jwtString);
			final JsonObject object = new JsonObject(decodedJWT.getClaim("TOKEN").asString());
			return object.mapTo(AccessToken.class);
		} catch (Exception e) {
			ExceptionUtility.throwGenericException(
				SecurityErrorCode.JWT_VERIFICATION_FAILED,
				String.format(
					"Acess token is invalid: %s",
					e.getMessage()
				)
			);
		}
		return null;
	}
}
