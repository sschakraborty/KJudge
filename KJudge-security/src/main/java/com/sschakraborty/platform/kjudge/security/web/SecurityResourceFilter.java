package com.sschakraborty.platform.kjudge.security.web;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.security.AccessToken;
import com.sschakraborty.platform.kjudge.security.crypto.Base64Utility;
import com.sschakraborty.platform.kjudge.security.jwt.JWTUtility;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

import java.util.StringTokenizer;

public class SecurityResourceFilter implements Handler<RoutingContext> {
	private final String loginPath;
	private final String redirectParamName;

	public SecurityResourceFilter(String loginPath, String redirectParamName) {
		this.loginPath = loginPath;
		this.redirectParamName = redirectParamName;
	}

	@Override
	public void handle(RoutingContext routingContext) {
		final HttpServerRequest request = routingContext.request();
		String authorization = request.getHeader("Authorization");

		if (authorization == null) {
			fail(routingContext);
		} else {
			authorization = authorization.trim();
			final StringTokenizer stringTokenizer = new StringTokenizer(authorization);
			if (stringTokenizer.countTokens() != 2) {
				fail(routingContext);
			} else {
				final String type = stringTokenizer.nextToken();
				final String jwtString = stringTokenizer.nextToken();
				if (!type.equals("Bearer")) {
					fail(routingContext);
				} else {
					handleRequest(jwtString, routingContext);
				}
			}
		}
	}

	private void handleRequest(String jwtString, RoutingContext routingContext) {
		try {
			final AccessToken accessToken = JWTUtility.decodeAccessToken(jwtString);
			routingContext.put("ACCESS_TOKEN", accessToken);
			routingContext.next();
		} catch (AbstractBusinessException e) {
			fail(routingContext);
		}
	}

	private void fail(RoutingContext routingContext) {
		routingContext.response().putHeader("Location", generateLocationHeader(routingContext));
		routingContext.fail(302);
	}

	private String generateLocationHeader(RoutingContext routingContext) {
		final String currentURL = getCurrentURL(routingContext);
		final StringBuilder locationHeader = new StringBuilder(this.loginPath);

		if (this.loginPath.contains("?")) {
			locationHeader.append("&");
		} else {
			locationHeader.append("?");
		}

		locationHeader.append(this.redirectParamName).append("=");
		locationHeader.append(currentURL);
		return locationHeader.toString();
	}

	private String getCurrentURL(RoutingContext routingContext) {
		final String url = routingContext.request().path();
		final String query = routingContext.request().query();
		final String retURL = (query != null) ? url + "?" + query : url;
		try {
			return Base64Utility.base64UrlEncode(retURL);
		} catch (AbstractBusinessException e) {
			return "";
		}
	}
}