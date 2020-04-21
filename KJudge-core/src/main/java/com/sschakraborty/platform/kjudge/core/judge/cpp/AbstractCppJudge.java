package com.sschakraborty.platform.kjudge.core.judge.cpp;

import com.sschakraborty.platform.kjudge.core.AbstractJudge;
import com.sschakraborty.platform.kjudge.core.exec.ProcessUtility;
import com.sschakraborty.platform.kjudge.core.exec.stageExecutor.CompileStageExecutor;
import com.sschakraborty.platform.kjudge.core.exec.stageExecutor.RunStageExecutor;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.Testcase;

public abstract class AbstractCppJudge extends AbstractJudge {
	private static final String OBJECT_NAME = "object";

	public AbstractCppJudge() throws AbstractBusinessException {
		super();
	}

	final void compileProgram(String baseDirectory, String std, String fileName) throws AbstractBusinessException {
		final CompileStageExecutor executor = new CompileStageExecutor(
			getProperties().getProperty("cpp.basePath"),
			getProperties().getProperty("cpp.compiler"),
			baseDirectory,
			true
		);
		executor.setExpectedOutput("");
		executor.setArguments("-O2", "-w", "-std=" + std, "-o", OBJECT_NAME, fileName);
		executor.execute();
	}

	final String runProgram(
		Submission submission,
		Testcase testcase,
		String baseDirectory,
		int timeLimit
	) throws AbstractBusinessException {
		final String runId = (
			submission.getSubmitter().getPrincipal()
				+ "_" + submission.getId() + "_"
				+ testcase.getName()
		);

		final RunStageExecutor stageExecutor = new RunStageExecutor(
			runId, baseDirectory, OBJECT_NAME, baseDirectory
		);

		stageExecutor.setTimeLimit(timeLimit);
		stageExecutor.setInputFilePath(testcase.getInputFilePath());
		stageExecutor.execute();

		return runId;
	}

	@Override
	protected void checkPropertiesPresent() throws AbstractBusinessException {
		final String[] keyNames = {
			"cpp.basePath",
			"cpp.compiler",
			"cpp.versionSwitch",
			"cpp.versionString"
		};

		for (String key : keyNames) {
			if (this.getProperties().getProperty(key) == null) {
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.JUDGE_PROPERTIES_ERROR,
					String.format(
						"C++ judge property (%s) is missing from config (.properties) file!",
						key
					)
				);
			}
		}
	}

	@Override
	protected void checkEnvironmentReady() throws AbstractBusinessException {
		String basePath = this.getProperties().getProperty("cpp.basePath");
		String compiler = this.getProperties().getProperty("cpp.compiler");
		String versionSwitch = this.getProperties().getProperty("cpp.versionSwitch");
		String versionString = this.getProperties().getProperty("cpp.versionString");

		String output = ProcessUtility.executeSystemCommand(basePath + "/" + compiler + " " + versionSwitch);
		if (!output.trim().startsWith(versionString)) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.JUDGE_ENVIRONMENT_NOT_READY,
				"C++ (g++) environment is not ready or missing!"
			);
		}
	}
}