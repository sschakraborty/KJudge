package com.sschakraborty.platform.kjudge.service.handlers.protectedHandler.handler;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import com.sschakraborty.platform.kjudge.security.AccessToken;
import com.sschakraborty.platform.kjudge.service.handlers.AbstractRouteHandler;
import com.sschakraborty.platform.kjudge.shared.model.UserProfile;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;

public class ProfileHandler extends AbstractRouteHandler {
	public ProfileHandler(GenericDAO genericDAO, TemplateEngine templateEngine) {
		super(genericDAO, templateEngine);
	}

	@Override
	public String[] getRouteURLArray() {
		return new String[]{
			"/profile/:handle",
			"/profile/",
			"/profile"
		};
	}

	@Override
	public void doGet(RoutingContext routingContext) {
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
			LoggingUtility.logger().error(e);
			routingContext.fail(500);
		}
	}

	@Override
	public void doPost(RoutingContext routingContext) {
		final JsonObject modelJSON = extractModel(routingContext);
		if (modelJSON == null) {
			routingContext.fail(400);
		} else {
			try {
				final UserProfile userProfile = modelJSON.getJsonObject("userProfile").mapTo(UserProfile.class);
				final AccessToken accessToken = fetchAccessToken(routingContext);
				if (userProfile == null || !accessToken.getUserPrincipal().equals(userProfile.getUserPrincipal())) {
					routingContext.fail(400);
				} else {
					getGenericDAO().update(userProfile);
					doGet(routingContext);
				}
			} catch (Exception e) {
				LoggingUtility.logger().error(e);
				routingContext.fail(400);
			}
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
