package com.sschakraborty.platform.kjudge.core;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.Submission;

public interface Judge {
	void performJudgement(Submission submission) throws AbstractBusinessException;

	boolean supports(Submission submission);
}