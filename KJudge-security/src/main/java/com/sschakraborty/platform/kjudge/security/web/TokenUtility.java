package com.sschakraborty.platform.kjudge.security.web;

import com.sschakraborty.platform.kjudge.security.AccessToken;
import io.vertx.ext.web.RoutingContext;

import java.sql.Date;
import java.time.LocalDate;

public class TokenUtility {
	public static AccessToken constructAccessToken(RoutingContext routingContext, String principal) {
		final AccessToken accessToken = new AccessToken();
		accessToken.setExpiryDate(Date.valueOf(LocalDate.now().plusDays(1)));
		accessToken.setRemoteAddress(getRemoteAddress(routingContext));
		accessToken.setRemotePort(getRemotePort(routingContext));
		accessToken.setUserPrincipal(principal);
		return accessToken;
	}

	private static int getRemotePort(RoutingContext routingContext) {
		return routingContext.request().remoteAddress().port();
	}

	private static String getRemoteAddress(RoutingContext routingContext) {
		final String host = routingContext.request().remoteAddress().host();
		final String path = routingContext.request().remoteAddress().path();
		return host + "@" + path;
	}
}