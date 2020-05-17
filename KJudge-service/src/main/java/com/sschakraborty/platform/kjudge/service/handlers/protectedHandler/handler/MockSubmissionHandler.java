package com.sschakraborty.platform.kjudge.service.handlers.protectedHandler.handler;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.StandardErrorCode;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import com.sschakraborty.platform.kjudge.security.AccessToken;
import com.sschakraborty.platform.kjudge.service.JudgeProcess;
import com.sschakraborty.platform.kjudge.service.handlers.AbstractRouteHandler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;

public class MockSubmissionHandler extends AbstractRouteHandler {
	public JudgeProcess judgeProcess;

	public MockSubmissionHandler(GenericDAO genericDAO, TemplateEngine templateEngine) {
		super(genericDAO, templateEngine);
	}

	public void setJudgeProcess(final JudgeProcess judgeProcess) {
		this.judgeProcess = judgeProcess;
	}

	@Override
	public String[] getRouteURLArray() {
		return new String[]{
			"/submission/mockSubmit"
		};
	}

	@Override
	public void doPost(RoutingContext routingContext) {
		handleMockSubmission(routingContext);
	}

	@Override
	public void doPut(RoutingContext routingContext) {
		handleMockSubmission(routingContext);
	}

	private void handleMockSubmission(RoutingContext routingContext) {
		try {
			final JsonObject model = extractModel(routingContext);
			final AccessToken accessToken = fetchAccessToken(routingContext);
			if (model != null) {
				this.judgeProcess.submitForJudgement(model, accessToken.getUserPrincipal());
			} else {
				ExceptionUtility.throwGenericException(
					StandardErrorCode.GENERIC_ERROR,
					"Extracted model was null or malformed!"
				);
			}
			routingContext.response().setStatusCode(200);
			routingContext.response().end();
		} catch (Exception e) {
			LoggingUtility.logger().error(e);
			routingContext.fail(400);
		}
	}
}