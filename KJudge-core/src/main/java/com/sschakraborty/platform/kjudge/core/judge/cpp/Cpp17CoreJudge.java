package com.sschakraborty.platform.kjudge.core.judge.cpp;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.Language;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.SubmissionResult;
import com.sschakraborty.platform.kjudge.shared.model.Testcase;

import java.util.Properties;

public class Cpp17CoreJudge extends AbstractCppJudge {
	private static final String FILE_NAME = "Cpp17_Solution.cpp";

	public Cpp17CoreJudge() throws AbstractBusinessException {
		super();
	}

	@Override
	public SubmissionResult performJudgement(Submission submission) throws AbstractBusinessException {
		String baseDirectory = writeSubmissionToStageArea(submission, FILE_NAME);
		compileProgram(baseDirectory, "c++17", FILE_NAME);

		int timeLimit = submission.getProblem().getTimeConstraint()
			.getTimeConstraints().get(Language.CPP_17);

		for (Testcase testcase : submission.getProblem().getTestcases()) {
			runProgram(submission, testcase, baseDirectory, timeLimit);
		}

		return null;
	}

	@Override
	public boolean supports(Submission submission) {
		return submission.getCodeSubmission().getLanguage() == Language.CPP_17;
	}

	@Override
	protected Properties readProperties() throws AbstractBusinessException {
		return this.getPropertyFileReader().readJudgeProperties(this.getClass());
	}
}