package com.sschakraborty.platform.kjudge.service.handlers.protectedHandler.handler;

import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.data.transactionManager.StatefulTransactionJob;
import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import com.sschakraborty.platform.kjudge.security.AccessToken;
import com.sschakraborty.platform.kjudge.service.handlers.AbstractRouteHandler;
import com.sschakraborty.platform.kjudge.shared.model.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.common.template.TemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateProblemHandler extends AbstractRouteHandler {
	public CreateProblemHandler(GenericDAO genericDAO, TemplateEngine templateEngine) {
		super(genericDAO, templateEngine);
	}

	@Override
	public String[] getRouteURLArray() {
		return new String[]{
			"/createProblem",
			"/createProblem/"
		};
	}

	@Override
	public void doGet(RoutingContext routingContext) {
		try {
			final JsonObject data = new JsonObject();
			final JsonArray languages = new JsonArray();
			for (Language language : Language.values()) {
				languages.add(language.toString());
			}
			data.put("languages", languages);
			renderByType(
				data,
				"template/Problem/ManageProblem",
				routingContext
			);
		} catch (Exception e) {
			LoggingUtility.logger().error(e);
			routingContext.fail(500);
		}
	}

	@Override
	public void doPost(final RoutingContext routingContext) {
		try {
			getGenericDAO().transactionManager().executeStatefulJob(new StatefulTransactionJob() {
				@Override
				public <T> List<T> execute(StatefulTransactionUnit transactionUnit) {
					final JsonObject model = extractModel(routingContext);
					if (model != null) {
						try {
							final JsonObject creationData = model.getJsonObject("creationData");
							final JsonObject problemJson = creationData.getJsonObject("problem");

							final AccessToken accessToken = fetchAccessToken(routingContext);
							final User currentUser = transactionUnit.getSession().find(User.class, accessToken.getUserPrincipal());

							Problem problem = transactionUnit.getSession().find(Problem.class, problemJson.getString("problemHandle"));
							if (problem == null) {
								problem = new Problem();
								problem.setTestcases(new ArrayList<>());
								problem.setSolutions(new ArrayList<>());
							}

							final CodingEvent codingEvent = transactionUnit.getSession().find(CodingEvent.class, problemJson.getString("codingEventHandle"));
							codingEvent.getProblemList().add(problem);

							problem.setCodingEvent(codingEvent);
							problem.setCreator(currentUser);
							problem.setProblemHandle(problemJson.getString("problemHandle"));
							problem.setName(problemJson.getString("name"));
							problem.setDescription(problemJson.getString("description"));

							for (Object object : creationData.getJsonArray("testcases")) {
								final JsonObject testcaseObject = (JsonObject) object;
								final Testcase testcase = testcaseObject.mapTo(Testcase.class);
								problem.getTestcases().add(testcase);
							}

							for (Object object : creationData.getJsonArray("solutions")) {
								final JsonObject solutionObject = (JsonObject) object;
								final CodeSubmission codeSubmission = solutionObject.mapTo(CodeSubmission.class);
								problem.getSolutions().add(codeSubmission);
							}

							final HashMap ioConstraints = creationData.getJsonObject("ioConstraints").mapTo(HashMap.class);
							final HashMap ioConstraintsLM = buildLanguageMap(ioConstraints);
							final IOConstraint ioConstraint = new IOConstraint();
							ioConstraint.setOutputLimits(ioConstraintsLM);
							problem.setIoConstraint(ioConstraint);

							final HashMap memoryConstraints = creationData.getJsonObject("memoryConstraints").mapTo(HashMap.class);
							final HashMap memoryConstraintsLM = buildLanguageMap(memoryConstraints);
							final MemoryConstraint memoryConstraint = new MemoryConstraint();
							memoryConstraint.setMemoryConstraints(memoryConstraintsLM);
							problem.setMemoryConstraint(memoryConstraint);

							final HashMap timeConstraints = creationData.getJsonObject("timeConstraints").mapTo(HashMap.class);
							final HashMap timeConstraintsLM = buildLanguageMap(timeConstraints);
							final TimeConstraint timeConstraint = new TimeConstraint();
							timeConstraint.setTimeConstraints(timeConstraintsLM);
							problem.setTimeConstraint(timeConstraint);

							transactionUnit.getSession().saveOrUpdate(problem);
							routingContext.response().putHeader("Location", String.format("/codingEvent/%s", codingEvent.getEventHandle()));
							routingContext.fail(302);
						} catch (Exception e) {
							LoggingUtility.logger().error(e);
							routingContext.fail(404);
						}
					} else {
						routingContext.fail(400);
					}
					return null;
				}
			});
		} catch (AbstractBusinessException e) {
			LoggingUtility.logger().error(e);
			routingContext.fail(500);
		}
	}

	private HashMap<Language, Integer> buildLanguageMap(HashMap map) {
		final HashMap<Language, Integer> languageStringHashMap = new HashMap<>();
		for (final Object key : map.keySet()) {
			final String stringKey = (String) key;
			final Language language = Language.valueOf(stringKey);
			languageStringHashMap.put(language, (Integer) map.get(key));
		}
		return languageStringHashMap;
	}
}