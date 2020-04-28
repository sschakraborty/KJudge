package com.sschakraborty.platform.kjudge.security;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.security.authentication.AuthenticationManager;
import com.sschakraborty.platform.kjudge.security.authentication.utility.AuthenticationPrincipalResolver;
import com.sschakraborty.platform.kjudge.security.web.SecurityLoginHandler;
import com.sschakraborty.platform.kjudge.security.web.SecurityResourceFilter;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;

public class Security {
	private static final String AUTH_ROOT = "/sso/auth";
	private static final String LOGIN_PATH = AUTH_ROOT + "/login";
	private static final String REDIRECT_PARAM_NAME = "redirect";
	private static final String STATIC_RESOURCE_PATH = "/resource";

	private final Router authRouter;
	private final SecurityLoginHandler loginHandler;
	private final SecurityResourceFilter filter;
	private final BodyHandler bodyHandler;
	private final FreeMarkerTemplateEngine templateEngine;
	private final StaticHandler staticHandler;

	private final AuthenticationPrincipalResolver resolver;
	private final AuthenticationManager authenticationManager;

	public Security(Vertx vertx, GenericDAO genericDAO) {
		this.bodyHandler = BodyHandler.create();
		this.authRouter = Router.router(vertx);
		this.filter = new SecurityResourceFilter(LOGIN_PATH, REDIRECT_PARAM_NAME);
		this.templateEngine = FreeMarkerTemplateEngine.create(vertx);
		this.templateEngine.setMaxCacheSize(0);
		this.staticHandler = StaticHandler.create("static");
		this.resolver = new AuthenticationPrincipalResolver(genericDAO);
		this.authenticationManager = new AuthenticationManager(this.resolver);
		this.loginHandler = new SecurityLoginHandler(
			REDIRECT_PARAM_NAME,
			this.templateEngine,
			this.authenticationManager
		);

		this.authRouter.route().handler(this.bodyHandler);
		this.authRouter.route(STATIC_RESOURCE_PATH + "/*").handler(this.staticHandler);
		this.authRouter.route(LOGIN_PATH).handler(this.loginHandler);
	}

	public Router router() {
		return this.authRouter;
	}

	public void applyFilter() {
		this.authRouter.route().handler(this.filter);
	}

	public void bindToServer(HttpServer server) {
		server.requestHandler(this.authRouter);
	}
}