package com.sschakraborty.platform.kjudge.service;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import com.sschakraborty.platform.kjudge.security.Security;
import com.sschakraborty.platform.kjudge.service.handlers.HandlerBundleProvider;
import com.sschakraborty.platform.kjudge.service.handlers.protectedHandler.ProtectedHandlerBundleProvider;
import com.sschakraborty.platform.kjudge.service.handlers.publicHandler.PublicHandlerBundleProvider;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.common.template.TemplateEngine;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;

public class Application {
	private final Vertx vertx;
	private final GenericDAO genericDAO;
	private final Security security;
	private final int portNumber;
	private final TemplateEngine templateEngine;
	private final ErrorHandler errorHandler;
	private final JudgeProcess judgeProcess;

	public Application(int portNumber, VertxOptions vertxOptions) throws AbstractBusinessException {
		this.vertx = Vertx.vertx(vertxOptions);
		this.genericDAO = new GenericDAO();
		this.security = new Security(this.vertx, this.genericDAO);
		this.portNumber = portNumber;
		this.templateEngine = FreeMarkerTemplateEngine.create(vertx);
		this.errorHandler = ErrorHandler.create();
		{
			this.judgeProcess = new JudgeProcess(this.genericDAO, 24);
			LoggingUtility.logger().info("Judge process started with {} threads!", this.judgeProcess.getThreadPoolSize());
		}
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
		provider = new ProtectedHandlerBundleProvider(this.genericDAO, this.templateEngine, this.judgeProcess);
		LoggingUtility.logger().info("Applying protected routes!");
		provider.applyRoutes(this.security.router());
		this.security.router().route().handler(this.errorHandler);
	}
}