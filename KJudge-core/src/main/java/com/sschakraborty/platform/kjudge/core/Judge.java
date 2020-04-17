package com.sschakraborty.platform.kjudge.core;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.SubmissionResult;

public interface Judge {
	SubmissionResult performJudgement(Submission submission) throws AbstractBusinessException;
}