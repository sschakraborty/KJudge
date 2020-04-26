package com.sschakraborty.platform.kjudge.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Assert;
import org.junit.Test;

public class JWTUtilityTest {
	@Test
	public void jwtTest() {
		String token = JWT.create()
			.withIssuer("ISSUER")
			.withSubject("TICKET_GRANTING_TICKET")
			.withClaim("CONTENT", "randomString")
			.sign(Algorithm.HMAC256("secret"));

		DecodedJWT verify = JWT.require(Algorithm.HMAC256("secret"))
			.withIssuer("ISSUER")
			.withSubject("TICKET_GRANTING_TICKET")
			.build()
			.verify(token);
		Assert.assertEquals(
			verify.getClaim("CONTENT").asString(),
			"randomString"
		);
	}
}