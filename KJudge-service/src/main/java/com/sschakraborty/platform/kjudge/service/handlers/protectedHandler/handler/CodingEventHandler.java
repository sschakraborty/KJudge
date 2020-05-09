package com.sschakraborty.platform.kjudge.service.handlers.protectedHandler.handler;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.GenericException;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import com.sschakraborty.platform.kjudge.service.codec.CodingEventWithoutProblemsCodec;
import com.sschakraborty.platform.kjudge.service.handlers.AbstractRouteHandler;
import com.sschakraborty.platform.kjudge.shared.model.CodingEvent;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;

import java.util.List;

public class CodingEventHandler extends AbstractRouteHandler {
	private final CodingEventWithoutProblemsCodec codec;

	public CodingEventHandler(GenericDAO genericDAO, TemplateEngine templateEngine) {
		super(genericDAO, templateEngine);
		this.codec = new CodingEventWithoutProblemsCodec();
	}

	@Override
	public String[] getRouteURLArray() {
		return new String[]{
			"/codingEvent",
			"/codingEvent/"
		};
	}

	@Override
	public void doGet(RoutingContext routingContext) {
		final JsonObject data = new JsonObject();
		try {
			data.put("allCodingEvents", putIntoJsonArray(fetchAllCodingEvents()));
			renderByType(
				data,
				"template/CodingEvent/CodingEvent",
				routingContext
			);
		} catch (Exception e) {
			LoggingUtility.logger().error(e);
			routingContext.fail(500);
		}
	}

	@Override
	public void doPost(RoutingContext routingContext) {
		try {
			final JsonObject model = extractModel(routingContext);
			final CodingEvent codingEvent = this.codec.decode(model);
			getGenericDAO().saveOrUpdate(codingEvent);
			doGet(routingContext);
		} catch (Exception e) {
			LoggingUtility.logger().error(e);
			routingContext.fail(400);
		}
	}

	@Override
	public void doPut(RoutingContext routingContext) {
		doPost(routingContext);
	}

	private JsonArray putIntoJsonArray(List<CodingEvent> allCodingEvents) throws GenericException {
		final JsonArray jsonArray = new JsonArray();
		for (CodingEvent codingEvent : allCodingEvents) {
			jsonArray.add(this.codec.encode(codingEvent));
		}
		return jsonArray;
	}

	private List<CodingEvent> fetchAllCodingEvents() throws AbstractBusinessException {
		return getGenericDAO().fetchAll(CodingEvent.class);
	}
}