package com.sschakraborty.platform.kjudge.core.judge.java;

import com.sschakraborty.platform.kjudge.core.exec.ProcessUtility;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;
import com.sschakraborty.platform.kjudge.shared.model.Language;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.SubmissionResult;

import java.util.Properties;

public class Java8CoreJudge extends AbstractJavaJudge {
	public Java8CoreJudge() throws AbstractBusinessException {
		super();
	}

	@Override
	public SubmissionResult performJudgement(Submission submission) throws AbstractBusinessException {
		writeSubmissionToStageArea(submission, "Solution.java");
		return null;
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