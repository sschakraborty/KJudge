package com.sschakraborty.platform.kjudge.service.handlers.protectedHandler.handler;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.data.transactionManager.StatefulTransactionJob;
import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import com.sschakraborty.platform.kjudge.security.AccessToken;
import com.sschakraborty.platform.kjudge.service.JudgeProcess;
import com.sschakraborty.platform.kjudge.service.handlers.AbstractRouteHandler;
import com.sschakraborty.platform.kjudge.shared.model.CodeSubmission;
import com.sschakraborty.platform.kjudge.shared.model.Problem;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.User;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;

import java.time.LocalDateTime;
import java.util.List;

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
		final JsonObject model = extractModel(routingContext);
		if (model != null) {
			try {
				getGenericDAO().transactionManager().executeStatefulJob(new StatefulTransactionJob() {
					@Override
					public <T> List<T> execute(StatefulTransactionUnit transactionUnit) {
						final CodeSubmission codeSubmission = model.getJsonObject("codeSubmission").mapTo(CodeSubmission.class);

						final AccessToken accessToken = fetchAccessToken(routingContext);
						final User user = transactionUnit.getSession().find(User.class, accessToken.getUserPrincipal());
						if (user == null) {
							routingContext.fail(400);
							return null;
						}

						final String problemHandle = model.getString("problemHandle");
						final Problem problem = transactionUnit.getSession().find(Problem.class, problemHandle);
						if (problem == null) {
							routingContext.fail(400);
							return null;
						}

						final Submission submission = new Submission();
						submission.setCodeSubmission(codeSubmission);
						submission.setProblem(problem);
						submission.setDateTime(LocalDateTime.now());
						submission.setSubmitter(user);

						transactionUnit.getSession().saveOrUpdate(submission);
						judgeProcess.submitForJudgement(submission);
						return null;
					}
				});
			} catch (Exception e) {
				LoggingUtility.logger().error(e);
				routingContext.fail(400);
			}
		} else {
			routingContext.fail(400);
		}
	}
}