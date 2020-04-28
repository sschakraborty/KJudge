package com.sschakraborty.platform.kjudge.security.web;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.security.crypto.Base64Utility;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;

public class SecurityLoginHandler implements Handler<RoutingContext> {
	private final String redirectParamName;
	private final TemplateEngine templateEngine;

	public SecurityLoginHandler(String redirectParamName, TemplateEngine templateEngine) {
		this.redirectParamName = redirectParamName;
		this.templateEngine = templateEngine;
	}

	@Override
	public void handle(RoutingContext routingContext) {
		try {
			final JsonObject bodyAsJson = routingContext.getBodyAsJson();
			final String principal = bodyAsJson.getString("principal");
			final String password = bodyAsJson.getString("password");

			if (principal == null || password == null) {
				serveLoginPage(routingContext);
			} else {
				serveLoginPage(routingContext);
			}
		} catch (Exception e) {
			serveLoginPage(routingContext);
		}
	}

	private void serveLoginPage(RoutingContext routingContext) {
		String redirectURL = getRedirectURL(routingContext);
		routingContext.response().end("Login! - " + redirectURL);
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
}