package com.sschakraborty.platform.kjudge.service.handlers;

import io.vertx.ext.web.Router;

public interface HandlerBundleProvider {
	void applyRoutes(Router router);
}