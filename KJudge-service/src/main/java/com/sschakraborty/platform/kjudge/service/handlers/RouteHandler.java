package com.sschakraborty.platform.kjudge.service.handlers;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public interface RouteHandler extends Handler<RoutingContext> {
	String[] getRouteURLArray();
}