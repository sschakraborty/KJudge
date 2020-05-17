package com.sschakraborty.platform.kjudge.service.handlers.protectedHandler;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.service.JudgeProcess;
import com.sschakraborty.platform.kjudge.service.handlers.HandlerBundleProvider;
import com.sschakraborty.platform.kjudge.service.handlers.RouteHandler;
import com.sschakraborty.platform.kjudge.service.handlers.protectedHandler.handler.*;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.common.template.TemplateEngine;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.Arrays;
import java.util.List;

public class ProtectedHandlerBundleProvider implements HandlerBundleProvider {
	private final List<RouteHandler> routeHandlers;
	private final StaticHandler staticHandler;

	public ProtectedHandlerBundleProvider(GenericDAO genericDAO, TemplateEngine templateEngine, JudgeProcess judgeProcess) {
		this.routeHandlers = Arrays.asList(
			new ProfileHandler(genericDAO, templateEngine),
			new CodingEventHandler(genericDAO, templateEngine),
			new CodingEventPageHandler(genericDAO, templateEngine),
			new ProblemPageHandler(genericDAO, templateEngine),
			new CreateProblemHandler(genericDAO, templateEngine),
			createMockSubmissionHandler(genericDAO, templateEngine, judgeProcess)
		);
		this.staticHandler = StaticHandler.create("static");
	}

	private final MockSubmissionHandler createMockSubmissionHandler(GenericDAO genericDAO, TemplateEngine templateEngine, JudgeProcess judgeProcess) {
		final MockSubmissionHandler mockSubmissionHandler = new MockSubmissionHandler(genericDAO, templateEngine);
		mockSubmissionHandler.setJudgeProcess(judgeProcess);
		return mockSubmissionHandler;
	}

	@Override
	public void applyRoutes(Router router) {
		router.route("/resources/*").handler(this.staticHandler);
		for (RouteHandler handler : this.routeHandlers) {
			for (String url : handler.getRouteURLArray()) {
				router.route(url).handler(handler);
			}
		}
	}
}