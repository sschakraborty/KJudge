package com.sschakraborty.platform.kjudge.service.handlers.protectedHandler.handler;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.service.handlers.AbstractRouteHandler;
import com.sschakraborty.platform.kjudge.shared.model.UserProfile;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;

public class ProfileHandler extends AbstractRouteHandler {
	public ProfileHandler(GenericDAO genericDAO, TemplateEngine templateEngine) {
		super(genericDAO, templateEngine);
	}

	@Override
	public String getRouteURL() {
		return "/profile/:handle";
	}

	@Override
	public HttpMethod getRouteMethod() {
		return HttpMethod.GET;
	}

	@Override
	public void handle(RoutingContext routingContext) {
		final String handle = generateHandle(routingContext);
		try {
			final UserProfile userProfile = getGenericDAO().fetchFull(UserProfile.class, handle);
			if (userProfile != null) {
				final JsonObject jsonObject = new JsonObject();
				jsonObject.put("userProfile", JsonObject.mapFrom(userProfile));
				renderByType(
					jsonObject,
					"template/UserProfile/UserProfile",
					routingContext
				);
			} else {
				routingContext.fail(404);
			}
		} catch (AbstractBusinessException e) {
			routingContext.fail(500);
		}
	}

	private String generateHandle(RoutingContext routingContext) {
		final String handle = routingContext.request().getParam("handle");
		if (handle == null) {
			return fetchAccessToken(routingContext).getUserPrincipal();
		} else {
			return handle;
		}
	}
}
