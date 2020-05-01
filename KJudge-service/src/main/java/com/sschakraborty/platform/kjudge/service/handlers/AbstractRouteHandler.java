package com.sschakraborty.platform.kjudge.service.handlers;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import com.sschakraborty.platform.kjudge.security.AccessToken;
import io.vertx.core.http.HttpMethod;
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

	@Override
	public final void handle(RoutingContext routingContext) {
		final HttpMethod method = routingContext.request().method();
		if (method == HttpMethod.GET) {
			doGet(routingContext);
		} else if (method == HttpMethod.POST) {
			doPost(routingContext);
		} else if (method == HttpMethod.PUT) {
			doPut(routingContext);
		} else if (method == HttpMethod.DELETE) {
			doDelete(routingContext);
		} else {
			doDefault(routingContext);
		}
	}

	public void doDefault(RoutingContext routingContext) {
		routingContext.fail(405);
	}

	public void doDelete(RoutingContext routingContext) {
		routingContext.fail(501);
	}

	public void doPut(RoutingContext routingContext) {
		routingContext.fail(501);
	}

	public void doPost(RoutingContext routingContext) {
		routingContext.fail(501);
	}

	public void doGet(RoutingContext routingContext) {
		routingContext.fail(501);
	}

	private String fetchAcceptHeader(final RoutingContext routingContext) {
		final String acceptHeader = routingContext.request().getHeader("Accept");
		if (acceptHeader == null || !acceptHeader.equals("application/json")) {
			return null;
		}
		return acceptHeader;
	}

	private void renderThroughTemplate(JsonObject data, String templatePath, RoutingContext routingContext) {
		final JsonObject modelData = new JsonObject();
		modelData.put("model", data.encodePrettily());
		this.getTemplateEngine().render(modelData, templatePath, result -> {
			if (result.succeeded()) {
				final JsonObject indexPageData = new JsonObject();
				indexPageData.put("pageTitle", "Generic Page Title");
				indexPageData.put("pageBody", result.result().toString());
				this.getTemplateEngine().render(indexPageData, "template/master/Index", finalResult -> {
					if (finalResult.succeeded()) {
						routingContext.response().end(finalResult.result());
					} else {
						routingContext.fail(500);
					}
				});
			} else {
				routingContext.fail(500);
			}
		});
	}

	protected JsonObject extractModel(RoutingContext routingContext) {
		try {
			return new JsonObject(routingContext.request().getFormAttribute("model"));
		} catch (Exception e) {
			LoggingUtility.logger().error(e);
			return null;
		}
	}
}