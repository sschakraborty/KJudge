package com.sschakraborty.platform.kjudge.core.judge.cpp;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.Language;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.SubmissionResult;
import com.sschakraborty.platform.kjudge.shared.model.Testcase;

import java.util.Properties;

public class Cpp14CoreJudge extends AbstractCppJudge {
	private static final String FILE_NAME = "Cpp14_Solution.cpp";

	public Cpp14CoreJudge() throws AbstractBusinessException {
		super();
	}

	@Override
	public void performJudgement(Submission submission) throws AbstractBusinessException {
		SubmissionResult submissionResult = fetchSubmissionResult(submission);
		String baseDirectory = writeSubmissionToStageArea(submission, FILE_NAME);

		try {
			compileProgram(baseDirectory, "c++14", FILE_NAME);
		} catch (AbstractBusinessException e) {
			fillCompilationError(submissionResult, e);
			throw e;
		}

		int timeLimit = submission.getProblem().getTimeConstraint()
			.getTimeConstraints().get(Language.CPP_14);

		String testcaseOutputFileName;

		for (Testcase testcase : submission.getProblem().getTestcases()) {
			try {
				testcaseOutputFileName = runProgram(
					submission,
					testcase,
					baseDirectory,
					timeLimit
				);

				compareOutput(
					baseDirectory,
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
		return submission.getCodeSubmission().getLanguage() == Language.CPP_14;
	}

	@Override
	protected Properties readProperties() throws AbstractBusinessException {
		return this.getPropertyFileReader().readJudgeProperties(this.getClass());
	}
}