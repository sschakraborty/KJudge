package com.sschakraborty.platform.kjudge.core.judge.clang;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.Language;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.SubmissionResult;
import com.sschakraborty.platform.kjudge.shared.model.Testcase;

import java.util.Properties;

public class CLang11CoreJudge extends AbstractCLangJudge {
	private static final String FILE_NAME = "C11_Solution.c";

	public CLang11CoreJudge() throws AbstractBusinessException {
		super();
	}

	@Override
	public SubmissionResult performJudgement(Submission submission) throws AbstractBusinessException {
		String baseDirectory = writeSubmissionToStageArea(submission, FILE_NAME);
		compileProgram(baseDirectory, "c11", FILE_NAME);

		int timeLimit = submission.getProblem().getTimeConstraint()
			.getTimeConstraints().get(Language.C_11);

		for (Testcase testcase : submission.getProblem().getTestcases()) {
			runProgram(submission, testcase, baseDirectory, timeLimit);
		}
		return null;
	}

	@Override
	public boolean supports(Submission submission) {
		return submission.getCodeSubmission().getLanguage() == Language.C_11;
	}

	@Override
	protected Properties readProperties() throws AbstractBusinessException {
		return this.getPropertyFileReader().readJudgeProperties(this.getClass());
	}
}