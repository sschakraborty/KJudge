package com.sschakraborty.platform.kjudge.service.handlers.protectedHandler.handler;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.data.transactionManager.StatefulTransactionJob;
import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import com.sschakraborty.platform.kjudge.service.handlers.AbstractRouteHandler;
import com.sschakraborty.platform.kjudge.shared.model.CodingEvent;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;

import java.util.Arrays;
import java.util.List;

public class CodingEventPageHandler extends AbstractRouteHandler {
	public CodingEventPageHandler(GenericDAO genericDAO, TemplateEngine templateEngine) {
		super(genericDAO, templateEngine);
	}

	@Override
	public String[] getRouteURLArray() {
		return new String[]{
			"/codingEvent/:handle"
		};
	}

	@Override
	public void doGet(RoutingContext routingContext) {
		final String eventHandle = routingContext.request().getParam("handle");
		if (eventHandle == null) {
			routingContext.fail(400);
		} else {
			try {
				final List<Object> list = getGenericDAO().transactionManager().executeStatefulJob(new StatefulTransactionJob() {
					@Override
					public List<Object> execute(StatefulTransactionUnit transactionUnit) {
						final CodingEvent codingEvent = transactionUnit.getSession().get(CodingEvent.class, eventHandle);
						if (codingEvent != null) {
							return Arrays.asList(JsonObject.mapFrom(codingEvent));
						}
						return null;
					}
				});
				final JsonObject codingEvent = (JsonObject) list.get(0);
				if (codingEvent == null) {
					routingContext.fail(404);
				} else {
					serveCodingEventPage(routingContext, codingEvent);
				}
			} catch (Exception e) {
				LoggingUtility.logger().error(e);
				routingContext.fail(500);
			}
		}
	}

	private void serveCodingEventPage(RoutingContext routingContext, JsonObject codingEvent) {
		final JsonObject data = new JsonObject();
		data.put("codingEvent", codingEvent);
		renderByType(
			data,
			"template/CodingEvent/CodingEventPage",
			routingContext
		);
	}
}