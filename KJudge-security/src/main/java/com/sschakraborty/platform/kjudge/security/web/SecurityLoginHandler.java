package com.sschakraborty.platform.kjudge.security.web;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.errorCode.SecurityErrorCode;
import com.sschakraborty.platform.kjudge.security.AccessToken;
import com.sschakraborty.platform.kjudge.security.authentication.AuthenticationManager;
import com.sschakraborty.platform.kjudge.security.credential.DatabaseAuthenticationCredential;
import com.sschakraborty.platform.kjudge.security.crypto.Base64Utility;
import com.sschakraborty.platform.kjudge.security.jwt.JWTUtility;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.http.Cookie;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;

public class SecurityLoginHandler implements Handler<RoutingContext> {
	private static final String GENERAL_MSG = "Welcome! Please login to continue!";
	private static final String INVALID_PRINCPAL = "User was not found in the system!";
	private static final String INVALID_PASSWORD = "Invalid password!";

	private final String redirectParamName;
	private final TemplateEngine templateEngine;
	private final AuthenticationManager authenticationManager;

	public SecurityLoginHandler(String redirectParamName, TemplateEngine templateEngine, AuthenticationManager authenticationManager) {
		this.redirectParamName = redirectParamName;
		this.templateEngine = templateEngine;
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void handle(RoutingContext routingContext) {
		try {
			final MultiMap formAttributes = routingContext.request().formAttributes();
			final String principal = formAttributes.get("principal");
			final String password = formAttributes.get("password");

			if (principal == null || password == null) {
				serveLoginPage(routingContext, GENERAL_MSG);
			} else {
				authenticate(routingContext, principal, password);
			}
		} catch (Exception e) {
			serveLoginPage(routingContext, GENERAL_MSG);
		}
	}

	private void authenticate(RoutingContext routingContext, String principal, String password) {
		final DatabaseAuthenticationCredential credential = new DatabaseAuthenticationCredential();
		credential.setUserPrincipal(principal);
		credential.setPassword(password);

		try {
			this.authenticationManager.authenticate(credential);
			handleSuccessfulAuthentication(routingContext, principal);
		} catch (AbstractBusinessException e) {
			if (e.getErrorCode() == SecurityErrorCode.INVALID_PRINCIPAL_LOGIN_ATTEMPT) {
				serveLoginPage(routingContext, INVALID_PRINCPAL);
			} else if (e.getErrorCode() == SecurityErrorCode.INVALID_CREDENTIALS) {
				serveLoginPage(routingContext, INVALID_PASSWORD);
			} else {
				serveLoginPage(routingContext, GENERAL_MSG);
			}
		}
	}

	private void handleSuccessfulAuthentication(RoutingContext routingContext, String principal) {
		final AccessToken accessToken = TokenUtility.constructAccessToken(routingContext, principal);
		try {
			final String jwtString = JWTUtility.encodeIntoJWTString(accessToken);
			postLoginRedirect(routingContext, jwtString);
		} catch (AbstractBusinessException e) {
			routingContext.fail(500);
		}
	}

	private void postLoginRedirect(RoutingContext routingContext, String jwtString) {
		final Cookie cookie = Cookie.cookie("Authorization", jwtString);
		cookie.setPath("/");
		cookie.setMaxAge(158132000l);
		routingContext.addCookie(cookie);
		routingContext.response().putHeader("Location", getRedirectURL(routingContext));
		routingContext.fail(302);
	}

	private void serveLoginPage(RoutingContext routingContext, String message) {
		final JsonObject loginPageData = new JsonObject();
		loginPageData.put("message", message);
		loginPageData.put("currentURL", getCurrentURL(routingContext));
		displayLoginPage(routingContext, loginPageData);
	}

	private void displayLoginPage(RoutingContext routingContext, JsonObject loginPageData) {
		this.templateEngine.render(loginPageData, "template/LoginPage", result -> {
			if (result.succeeded()) {
				routingContext.response().end(result.result());
			} else {
				routingContext.fail(500);
			}
		});
	}

	private String getRedirectURL(RoutingContext routingContext) {
		String redirectURL = routingContext.request().getParam(this.redirectParamName);
		if (redirectURL == null) {
			redirectURL = "/";
		} else {
			redirectURL = decodeURL(redirectURL);
		}
		return redirectURL;
	}

	private String decodeURL(String redirectURL) {
		try {
			return Base64Utility.base64UrlDecode(redirectURL);
		} catch (AbstractBusinessException e) {
			return "/";
		}
	}

	private String getCurrentURL(RoutingContext routingContext) {
		final String url = routingContext.request().path();
		final String query = routingContext.request().query();
		return (query != null) ? url + "?" + query : url;
	}
}