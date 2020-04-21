package com.sschakraborty.platform.kjudge.core.judge.clang;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.Language;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.SubmissionResult;
import com.sschakraborty.platform.kjudge.shared.model.Testcase;

import java.util.Properties;

public class CLang99CoreJudge extends AbstractCLangJudge {
	private static final String FILE_NAME = "C99_Solution.c";

	public CLang99CoreJudge() throws AbstractBusinessException {
		super();
	}

	@Override
	public SubmissionResult performJudgement(Submission submission) throws AbstractBusinessException {
		String baseDirectory = writeSubmissionToStageArea(submission, FILE_NAME);
		compileProgram(baseDirectory, "c99", FILE_NAME);

		int timeLimit = submission.getProblem().getTimeConstraint()
			.getTimeConstraints().get(Language.C_99);

		String testcaseOutputFileName;

		for (Testcase testcase : submission.getProblem().getTestcases()) {
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
		}
		return null;
	}

	@Override
	public boolean supports(Submission submission) {
		return submission.getCodeSubmission().getLanguage() == Language.C_99;
	}

	@Override
	protected Properties readProperties() throws AbstractBusinessException {
		return this.getPropertyFileReader().readJudgeProperties(this.getClass());
	}
}