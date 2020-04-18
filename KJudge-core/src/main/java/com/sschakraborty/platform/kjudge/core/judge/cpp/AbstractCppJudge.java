package com.sschakraborty.platform.kjudge.core.judge.cpp;

import com.sschakraborty.platform.kjudge.core.AbstractJudge;
import com.sschakraborty.platform.kjudge.core.exec.ProcessUtility;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;

public abstract class AbstractCppJudge extends AbstractJudge {
	public AbstractCppJudge() throws AbstractBusinessException {
		super();
	}

	@Override
	protected void checkEnvironmentReady() throws AbstractBusinessException {
		String output = ProcessUtility.executeSystemCommand("g++ --version");
		if (!output.startsWith("g++ ")) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.JUDGE_ENVIRONMENT_NOT_READY,
				"C++ (g++) environment is not ready or missing!"
			);
		}
	}
}