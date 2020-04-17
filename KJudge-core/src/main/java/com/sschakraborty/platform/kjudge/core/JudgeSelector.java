package com.sschakraborty.platform.kjudge.core;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;
import com.sschakraborty.platform.kjudge.shared.model.Submission;

import java.util.ArrayList;
import java.util.List;

public class JudgeSelector {
	private List<Judge> judges;

	public JudgeSelector() {
		this.judges = new ArrayList<>();
	}

	public void addJudge(Judge judge) {
		if (judge != null) {
			this.judges.add(judge);
		}
	}

	public Judge select(Submission submission) throws AbstractBusinessException {
		for (Judge judge : this.judges) {
			if (judge.supports(submission)) {
				return judge;
			}
		}
		ExceptionUtility.throwGenericException(JudgeErrorCode.UNSUPPORTED_LANGUAGE, "A suitable judge for the language was not found!");
		return null;
	}
}