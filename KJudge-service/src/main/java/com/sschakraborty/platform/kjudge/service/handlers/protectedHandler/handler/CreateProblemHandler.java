package com.sschakraborty.platform.kjudge.service.handlers.protectedHandler.handler;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import com.sschakraborty.platform.kjudge.service.handlers.AbstractRouteHandler;
import com.sschakraborty.platform.kjudge.shared.model.Language;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;

public class CreateProblemHandler extends AbstractRouteHandler {
	public CreateProblemHandler(GenericDAO genericDAO, TemplateEngine templateEngine) {
		super(genericDAO, templateEngine);
	}

	@Override
	public String[] getRouteURLArray() {
		return new String[]{
			"/manageProblem",
			"/manageProblem/"
		};
	}

	@Override
	public void doGet(RoutingContext routingContext) {
		try {
			final JsonObject data = new JsonObject();
			final JsonArray languages = new JsonArray();
			for (Language language : Language.values()) {
				languages.add(language.toString());
			}
			data.put("languages", languages);
			renderByType(
				data,
				"template/Problem/ManageProblem",
				routingContext
			);
		} catch (Exception e) {
			LoggingUtility.logger().error(e);
			routingContext.fail(500);
		}
	}
}