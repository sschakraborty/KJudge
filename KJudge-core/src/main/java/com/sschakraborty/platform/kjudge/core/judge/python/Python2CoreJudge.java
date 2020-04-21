package com.sschakraborty.platform.kjudge.core.judge.python;

import com.sschakraborty.platform.kjudge.core.exec.ProcessUtility;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;
import com.sschakraborty.platform.kjudge.shared.model.Language;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.SubmissionResult;
import com.sschakraborty.platform.kjudge.shared.model.Testcase;

import java.util.Properties;

public class Python2CoreJudge extends AbstractPythonJudge {
	private static final String FILE_NAME = "Solution_Py2.py";

	public Python2CoreJudge() throws AbstractBusinessException {
		super();
	}

	@Override
	public SubmissionResult performJudgement(Submission submission) throws AbstractBusinessException {
		String baseDirectory = writeSubmissionToStageArea(submission, FILE_NAME);
		int timeLimit = submission.getProblem().getTimeConstraint()
			.getTimeConstraints().get(Language.PYTHON_2);

		String testcaseOutputFileName;

		for (Testcase testcase : submission.getProblem().getTestcases()) {
			testcaseOutputFileName = runProgram(
				submission,
				testcase,
				FILE_NAME,
				baseDirectory,
				timeLimit
			);

			compareOutput(
				baseDirectory,
				testcaseOutputFileName,
				testcase.getExpectedOutputFilePath()
			);
		}
		return null;
	}

	@Override
	public boolean supports(Submission submission) {
		return submission.getCodeSubmission().getLanguage() == Language.PYTHON_2;
	}

	@Override
	protected void checkEnvironmentReady() throws AbstractBusinessException {
		String basePath = this.getProperties().getProperty("python.basePath");
		String program = this.getProperties().getProperty("python.program");
		String versionSwitch = this.getProperties().getProperty("python.versionSwitch");
		String versionString = this.getProperties().getProperty("python.versionString");

		String output = ProcessUtility.executeSystemCommand(basePath + "/" + program + " " + versionSwitch);
		if (!output.trim().startsWith(versionString)) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.JUDGE_ENVIRONMENT_NOT_READY,
				"Python 2 environment is not ready or missing!"
			);
		}
	}

	@Override
	protected Properties readProperties() throws AbstractBusinessException {
		return this.getPropertyFileReader().readJudgeProperties(this.getClass());
	}
}