package com.sschakraborty.platform.kjudge.core.judge.java;

import com.sschakraborty.platform.kjudge.core.exec.ProcessUtility;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;
import com.sschakraborty.platform.kjudge.shared.model.Language;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.SubmissionResult;
import com.sschakraborty.platform.kjudge.shared.model.Testcase;

import java.util.Properties;

public class Java8CoreJudge extends AbstractJavaJudge {
	private static final String FILE_NAME = "Solution_Java_8.java";

	public Java8CoreJudge() throws AbstractBusinessException {
		super();
	}

	@Override
	public void performJudgement(Submission submission) throws AbstractBusinessException {
		SubmissionResult submissionResult = fetchSubmissionResult(submission);
		String baseDir = writeSubmissionToStageArea(submission, FILE_NAME);

		try {
			compileProgram(baseDir, FILE_NAME);
		} catch (AbstractBusinessException e) {
			fillCompilationError(submissionResult, e);
			throw e;
		}

		String mainClass = getMainClassName(baseDir);

		int timeConstraint = submission.getProblem().getTimeConstraint()
			.getTimeConstraints().get(Language.JAVA_8);

		String testcaseOutputFileName;

		for (Testcase testcase : submission.getProblem().getTestcases()) {
			try {
				testcaseOutputFileName = runProgram(
					submission,
					testcase,
					mainClass,
					baseDir,
					timeConstraint
				);

				compareOutput(
					baseDir,
					testcaseOutputFileName,
					testcase.getExpectedOutputFilePath()
				);

				fillSuccess(submissionResult, testcase);
			} catch (AbstractBusinessException e) {
				if (fillRuntimeError(submissionResult, testcase, e)) {
					throw e;
				}
			}
		}
	}

	@Override
	public boolean supports(Submission submission) {
		return submission.getCodeSubmission().getLanguage() == Language.JAVA_8;
	}

	@Override
	protected void checkEnvironmentReady() throws AbstractBusinessException {
		String basePath = this.getProperties().getProperty("jvm.basePath");
		String compiler = this.getProperties().getProperty("jvm.compiler");
		String versionSwitch = this.getProperties().getProperty("jvm.compiler.versionSwitch");
		String versionString = this.getProperties().getProperty("jvm.compiler.versionString");

		String output = ProcessUtility.executeSystemCommand(basePath + "/bin/" + compiler + " " + versionSwitch);
		if (!output.trim().equals(versionString)) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.JUDGE_ENVIRONMENT_NOT_READY,
				"Java 8 (JDK) environment is not ready or missing!"
			);
		}
	}

	@Override
	protected Properties readProperties() throws AbstractBusinessException {
		return this.getPropertyFileReader().readJudgeProperties(this.getClass());
	}
}