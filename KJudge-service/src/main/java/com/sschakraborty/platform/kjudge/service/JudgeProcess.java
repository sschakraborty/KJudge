package com.sschakraborty.platform.kjudge.service;

import com.sschakraborty.platform.kjudge.core.JudgeEntryPoint;
import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.data.transactionManager.StatefulTransactionJob;
import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.StandardErrorCode;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import com.sschakraborty.platform.kjudge.shared.model.CodeSubmission;
import com.sschakraborty.platform.kjudge.shared.model.Problem;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.User;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JudgeProcess {
	private final int threadPoolSize;
	private final ExecutorService judgeProcessExecutor;
	private final JudgeEntryPoint judgeEntryPoint;
	private final GenericDAO genericDAO;

	public JudgeProcess(final GenericDAO genericDAO, final int threadPoolSize) throws AbstractBusinessException {
		this.threadPoolSize = threadPoolSize;
		this.judgeProcessExecutor = Executors.newFixedThreadPool(threadPoolSize);
		this.judgeEntryPoint = new JudgeEntryPoint();
		this.genericDAO = genericDAO;
	}

	public void submitForJudgement(final JsonObject submission, final String userPrincipal) {
		try {
			assess(submission);
			assertNotNull(userPrincipal);
			this.judgeProcessExecutor.submit(buildRunnableJob(submission, userPrincipal));
		} catch (AbstractBusinessException e) {
			LoggingUtility.logger().error(e);
		}
	}

	private Runnable buildRunnableJob(final JsonObject submission, final String userPrincipal) {
		return () -> {
			try {
				genericDAO.transactionManager().executeStatefulJob(new StatefulTransactionJob() {
					@Override
					public <T> List<T> execute(StatefulTransactionUnit transactionUnit) throws AbstractBusinessException {
						final CodeSubmission codeSubmission = submission.getJsonObject("codeSubmission").mapTo(CodeSubmission.class);
						final User user = transactionUnit.getSession().find(User.class, userPrincipal);

						final String problemHandle = submission.getString("problemHandle");
						final Problem problem = transactionUnit.getSession().find(Problem.class, problemHandle);
						if (problem == null) {
							ExceptionUtility.throwGenericException(
								StandardErrorCode.GENERIC_ERROR,
								"Specified problem was not found!"
							);
							return null;
						}

						final Submission submission = new Submission();
						submission.setCodeSubmission(codeSubmission);
						submission.setProblem(problem);
						submission.setDateTime(LocalDateTime.now());
						submission.setSubmitter(user);

						transactionUnit.getSession().save(submission);
						try {
							judgeEntryPoint.performJudgement(submission);
						} catch (Exception e) {
							LoggingUtility.logger().error(e);
						} finally {
							transactionUnit.getSession().saveOrUpdate(submission);
						}
						return null;
					}
				});
			} catch (AbstractBusinessException e) {
				LoggingUtility.logger().error(e);
			}
		};
	}

	private void assess(JsonObject submission) throws AbstractBusinessException {
		assertNotNull(submission.getString("problemHandle"));
		assertNotNull(submission.getJsonObject("codeSubmission"));
	}

	private void assertNotNull(Object object) throws AbstractBusinessException {
		if (object == null) {
			ExceptionUtility.throwGenericException(
				StandardErrorCode.OBJECT_CANNOT_BE_NULL,
				String.format(
					"Assertion failed: Object (%s) cannot be null",
					object.getClass().getSimpleName()
				)
			);
		}
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}
}