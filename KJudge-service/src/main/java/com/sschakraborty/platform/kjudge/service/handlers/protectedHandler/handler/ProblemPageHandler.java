package com.sschakraborty.platform.kjudge.service.handlers.protectedHandler.handler;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import com.sschakraborty.platform.kjudge.service.handlers.AbstractRouteHandler;
import com.sschakraborty.platform.kjudge.shared.model.Problem;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;

public class ProblemPageHandler extends AbstractRouteHandler {
	public ProblemPageHandler(GenericDAO genericDAO, TemplateEngine templateEngine) {
		super(genericDAO, templateEngine);
	}

	@Override
	public String[] getRouteURLArray() {
		return new String[]{
			"/problem/:handle"
		};
	}

	@Override
	public void doGet(RoutingContext routingContext) {
		final String handle = routingContext.request().getParam("handle");
		if (handle != null) {
			try {
				final Problem problem = getGenericDAO().fetchFull(Problem.class, handle);
				final JsonObject data = new JsonObject();
				data.put("problem", JsonObject.mapFrom(problem));
				renderByType(
					data,
					"template/Problem/Problem",
					routingContext
				);
			} catch (AbstractBusinessException e) {
				LoggingUtility.logger().error(e);
				routingContext.fail(500);
			}
		} else {
			routingContext.fail(400);
		}
	}
}
