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

public class Java11CoreJudge extends AbstractJavaJudge {
	private static final String FILE_NAME = "Solution_Java_11.java";

	public Java11CoreJudge() throws AbstractBusinessException {
		super();
	}

	@Override
	public SubmissionResult performJudgement(Submission submission) throws AbstractBusinessException {
		String baseDir = writeSubmissionToStageArea(submission, FILE_NAME);
		compileProgram(baseDir, FILE_NAME);
		String mainClass = getMainClassName(baseDir);

		int timeConstraint = submission.getProblem().getTimeConstraint()
			.getTimeConstraints().get(Language.JAVA_8);

		for (Testcase testcase : submission.getProblem().getTestcases()) {
			runProgram(
				submission,
				testcase,
				mainClass,
				baseDir,
				timeConstraint
			);
		}

		return null;
	}

	@Override
	public boolean supports(Submission submission) {
		return submission.getCodeSubmission().getLanguage() == Language.JAVA_11;
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
				"Java 11 (JDK) environment is not ready or missing!"
			);
		}
	}

	@Override
	protected Properties readProperties() throws AbstractBusinessException {
		return this.getPropertyFileReader().readJudgeProperties(this.getClass());
	}
}