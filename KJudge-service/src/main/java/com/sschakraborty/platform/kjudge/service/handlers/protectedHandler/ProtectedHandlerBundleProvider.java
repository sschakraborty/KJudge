package com.sschakraborty.platform.kjudge.service.handlers.protectedHandler;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.service.handlers.HandlerBundleProvider;
import com.sschakraborty.platform.kjudge.service.handlers.RouteHandler;
import com.sschakraborty.platform.kjudge.service.handlers.protectedHandler.handler.ProfileHandler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.common.template.TemplateEngine;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.Arrays;
import java.util.List;

public class ProtectedHandlerBundleProvider implements HandlerBundleProvider {
	private final List<RouteHandler> routeHandlers;
	private final StaticHandler staticHandler;

	public ProtectedHandlerBundleProvider(GenericDAO genericDAO, TemplateEngine templateEngine) {
		this.routeHandlers = Arrays.asList(
			new ProfileHandler(genericDAO, templateEngine)
		);
		this.staticHandler = StaticHandler.create("static");
	}

	@Override
	public void applyRoutes(Router router) {
		router.route("/resources/*").handler(this.staticHandler);
		for (RouteHandler handler : this.routeHandlers) {
			for (String url : handler.getRouteURLArray()) {
				router.route(handler.getRouteMethod(), url).handler(handler);
			}
		}
	}
}