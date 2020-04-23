package com.sschakraborty.platform.kjudge.core;


import com.sschakraborty.platform.kjudge.core.io.JudgeListReader;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;
import com.sschakraborty.platform.kjudge.shared.model.Submission;

import java.lang.reflect.InvocationTargetException;

public class JudgeEntryPoint {
	private final JudgeSelector judgeSelector;
	private final JudgeListReader judgeListReader;

	public JudgeEntryPoint() throws AbstractBusinessException {
		this.judgeSelector = new JudgeSelector();
		this.judgeListReader = new JudgeListReader();
		this.initializeJudges();
	}

	private void initializeJudges() throws AbstractBusinessException {
		for (Class<? extends Judge> judgeClass : this.judgeListReader.getActiveJudges()) {
			try {
				this.judgeSelector.addJudge(judgeClass.getConstructor().newInstance());
			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.MALFORMED_JUDGE_CLASS,
					String.format(
						"Judge (%s) doesn't have a public default constructor or not instantiable!",
						judgeClass.getName()
					)
				);
			} catch (InvocationTargetException e) {
				throw (AbstractBusinessException) e.getCause();
			}
		}
	}

	public void performJudgement(Submission submission) throws AbstractBusinessException {
		this.judgeSelector.select(submission).performJudgement(submission);
	}
}