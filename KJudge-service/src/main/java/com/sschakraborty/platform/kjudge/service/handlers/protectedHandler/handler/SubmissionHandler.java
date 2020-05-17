package com.sschakraborty.platform.kjudge.service.handlers.protectedHandler.handler;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.data.transactionManager.StatefulTransactionJob;
import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import com.sschakraborty.platform.kjudge.security.AccessToken;
import com.sschakraborty.platform.kjudge.service.handlers.AbstractRouteHandler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;
import org.hibernate.query.Query;

import java.util.List;

public class SubmissionHandler extends AbstractRouteHandler {
	private static final String QUERY = "select new map (" +
		"s.problem.problemHandle as problemHandle," +
		"s.problem.name as problemName," +
		"s.dateTime as dateTime," +
		"s.codeSubmission.language as language," +
		"s.codeSubmission.sourceCode as sourceCode," +
		"s.submissionResult.outputCode as outputCode," +
		"s.submissionResult.compilationError as isCompilationError," +
		"s.submissionResult.compilationErrorMessage as compilationErrorMessage" +
		") from Submission s where s.submitter.principal = :userPrincipal order by s.dateTime desc";

	public SubmissionHandler(GenericDAO genericDAO, TemplateEngine templateEngine) {
		super(genericDAO, templateEngine);
	}

	@Override
	public String[] getRouteURLArray() {
		return new String[]{
			"/submission",
			"/submission/"
		};
	}

	@Override
	public void doGet(RoutingContext routingContext) {
		final AccessToken accessToken = fetchAccessToken(routingContext);
		final String userPrincipal = accessToken.getUserPrincipal();

		try {
			getGenericDAO().transactionManager().executeStatefulJob(new StatefulTransactionJob() {
				@Override
				public <T> List<T> execute(StatefulTransactionUnit transactionUnit) {
					final Query query = transactionUnit.getSession().createQuery(QUERY).setMaxResults(20);
					final List list = query.setParameter("userPrincipal", userPrincipal).list();
					final JsonObject data = new JsonObject();
					data.put("submissionResult", new JsonArray(list));
					renderByType(
						data,
						"template/Submission/Submission",
						routingContext
					);
					return null;
				}
			});
		} catch (AbstractBusinessException e) {
			LoggingUtility.logger().error(e);
			routingContext.fail(400);
		}
	}
}