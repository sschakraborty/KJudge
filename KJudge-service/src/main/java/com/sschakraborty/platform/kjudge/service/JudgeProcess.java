package com.sschakraborty.platform.kjudge.service;

import com.sschakraborty.platform.kjudge.core.JudgeEntryPoint;
import com.sschakraborty.platform.kjudge.data.GenericDAO;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.StandardErrorCode;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import com.sschakraborty.platform.kjudge.shared.model.Submission;

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

	public void submitForJudgement(final Submission submission) {
		try {
			assess(submission);
			this.judgeProcessExecutor.submit(buildRunnableJob(submission));
		} catch (AbstractBusinessException e) {
			LoggingUtility.logger().error(e);
		}
	}

	private Runnable buildRunnableJob(final Submission submission) {
		return () -> {
			try {
				this.judgeEntryPoint.performJudgement(submission);
			} catch (AbstractBusinessException e) {
				LoggingUtility.logger().error(e);
			} finally {
				if (submission.getSubmissionResult() != null) {
					try {
						this.genericDAO.saveOrUpdate(submission);
					} catch (AbstractBusinessException e) {
						LoggingUtility.logger().error(e);
					}
				}
			}
		};
	}

	private void assess(Submission submission) throws AbstractBusinessException {
		assertNotNull(submission.getProblem());
		assertNotNull(submission.getSubmitter());
		assertNotNull(submission.getCodeSubmission());
		assertNotNull(submission.getDateTime());
	}

	private void assertNotNull(Object object) throws AbstractBusinessException {
		if (object == null) {
			ExceptionUtility.throwGenericException(
				StandardErrorCode.OBJECT_CANNOT_BE_NULL,
				"Assertion failed for submission!"
			);
		}
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}
}