package com.sschakraborty.platform.kjudge.service;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import com.sschakraborty.platform.kjudge.security.Security;
import com.sschakraborty.platform.kjudge.service.handlers.HandlerBundleProvider;
import com.sschakraborty.platform.kjudge.service.handlers.protectedHandler.ProtectedHandlerBundleProvider;
import com.sschakraborty.platform.kjudge.service.handlers.publicHandler.PublicHandlerBundleProvider;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.common.template.TemplateEngine;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;

public class Application {
	private final Vertx vertx;
	private final GenericDAO genericDAO;
	private final Security security;
	private final int portNumber;
	private final TemplateEngine templateEngine;

	public Application(int portNumber) throws AbstractBusinessException {
		this.vertx = Vertx.vertx();
		this.genericDAO = new GenericDAO();
		this.security = new Security(this.vertx, this.genericDAO);
		this.portNumber = portNumber;
		this.templateEngine = FreeMarkerTemplateEngine.create(vertx);
	}

	public void init() {
		this.applyRoutes();
		this.fireUpServer();
	}

	private void fireUpServer() {
		final HttpServer httpServer = this.vertx.createHttpServer();
		this.security.bindToServer(httpServer);
		httpServer.listen(this.portNumber);
		LoggingUtility.logger().info("Thin client running at port {}!", this.portNumber);
	}

	private void applyRoutes() {
		HandlerBundleProvider provider = new PublicHandlerBundleProvider();
		LoggingUtility.logger().info("Applying public routes!");
		provider.applyRoutes(this.security.router());
		this.security.applyFilter();
		provider = new ProtectedHandlerBundleProvider(this.genericDAO, this.templateEngine);
		LoggingUtility.logger().info("Applying protected routes!");
		provider.applyRoutes(this.security.router());
	}
}