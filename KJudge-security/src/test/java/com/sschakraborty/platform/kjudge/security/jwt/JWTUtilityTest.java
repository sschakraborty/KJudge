package com.sschakraborty.platform.kjudge.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.security.AccessToken;
import com.sschakraborty.platform.kjudge.security.crypto.SecurityConstant;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

public class JWTUtilityTest {
	@Test
	public void jwtTest() {
		String token = JWT.create()
			.withIssuer("ISSUER")
			.withSubject("TICKET_GRANTING_TICKET")
			.withClaim("CONTENT", "randomString")
			.sign(Algorithm.HMAC384(SecurityConstant.SHA_SECRET_KEY));

		DecodedJWT verify = JWT.require(Algorithm.HMAC384(SecurityConstant.SHA_SECRET_KEY))
			.withIssuer("ISSUER")
			.withSubject("TICKET_GRANTING_TICKET")
			.build()
			.verify(token);
		Assert.assertEquals(
			verify.getClaim("CONTENT").asString(),
			"randomString"
		);
	}

	@Test
	public void testToken() throws AbstractBusinessException {
		AccessToken accessToken = new AccessToken();
		accessToken.setExpiryDate(Date.valueOf(LocalDate.now().plusDays(5)));
		String token = JWTUtility.encodeIntoJWTString(accessToken);
		Assert.assertTrue(token != null);
	}
}