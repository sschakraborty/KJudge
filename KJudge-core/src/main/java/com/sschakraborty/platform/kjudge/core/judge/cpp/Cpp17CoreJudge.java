package com.sschakraborty.platform.kjudge.core.judge.cpp;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.Language;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.SubmissionResult;

import java.util.Properties;

public class Cpp17CoreJudge extends AbstractCppJudge {
	public Cpp17CoreJudge() throws AbstractBusinessException {
		super();
	}

	@Override
	public SubmissionResult performJudgement(Submission submission) throws AbstractBusinessException {
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