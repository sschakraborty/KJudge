package com.sschakraborty.platform.kjudge.core.judge.python;

import com.sschakraborty.platform.kjudge.core.AbstractJudge;
import com.sschakraborty.platform.kjudge.core.exec.stageExecutor.RunStageExecutor;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.Testcase;

public abstract class AbstractPythonJudge extends AbstractJudge {
	public AbstractPythonJudge() throws AbstractBusinessException {
		super();
	}

	protected final void runProgram(
		Submission submission,
		Testcase testcase,
		String fileName,
		String baseDirectory,
		int timeLimit
	) throws AbstractBusinessException {
		final String runId = (
			submission.getSubmitter().getPrincipal()
				+ "_" + submission.getId() + "_"
				+ testcase.getName()
		);
		String basePath = this.getProperties().getProperty("python.basePath");
		String runtime = getProperties().getProperty("python.program");

		final RunStageExecutor stageExecutor = new RunStageExecutor(
			runId, basePath, runtime, baseDirectory
		);

		stageExecutor.setArguments(fileName);
		stageExecutor.setTimeLimit(timeLimit);
		stageExecutor.setInputFilePath(testcase.getInputFilePath());
		stageExecutor.execute();
	}

	@Override
	protected void checkPropertiesPresent() throws AbstractBusinessException {
		final String[] keyNames = {
			"python.basePath",
			"python.program",
			"python.versionSwitch",
			"python.versionString"
		};

		for (String key : keyNames) {
			if (this.getProperties().getProperty(key) == null) {
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.JUDGE_PROPERTIES_ERROR,
					String.format(
						"Python judge property (%s) is missing from config (.properties) file!",
						key
					)
				);
			}
		}
	}
}