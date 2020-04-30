package com.sschakraborty.platform.kjudge.service.handlers;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.security.AccessToken;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;

public abstract class AbstractRouteHandler implements RouteHandler {
	private final TemplateEngine templateEngine;
	private final GenericDAO genericDAO;

	public AbstractRouteHandler(GenericDAO genericDAO, TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
		this.genericDAO = genericDAO;
	}

	public TemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public GenericDAO getGenericDAO() {
		return genericDAO;
	}

	public AccessToken fetchAccessToken(RoutingContext routingContext) {
		return (AccessToken) routingContext.get("ACCESS_TOKEN");
	}

	public void renderByType(JsonObject data, String templatePath, RoutingContext context) {
		final String acceptHeader = fetchAcceptHeader(context);
		if (acceptHeader == null) {
			renderThroughTemplate(data, templatePath, context);
		} else if (acceptHeader.equals("application/json")) {
			context.response().putHeader("Content-type", acceptHeader);
			context.response().end(data.encodePrettily());
		} else {
			context.fail(406);
		}
	}

	private String fetchAcceptHeader(final RoutingContext routingContext) {
		final String acceptHeader = routingContext.request().getHeader("Accept");
		if (acceptHeader == null || !acceptHeader.equals("application/json")) {
			return null;
		}
		return acceptHeader;
	}

	private void renderThroughTemplate(
		JsonObject data,
		String templatePath,
		RoutingContext routingContext
	) {
		this.getTemplateEngine().render(data, templatePath, result -> {
			if (result.succeeded()) {
				routingContext.response().end(result.result());
			} else {
				routingContext.fail(500);
			}
		});
	}
}