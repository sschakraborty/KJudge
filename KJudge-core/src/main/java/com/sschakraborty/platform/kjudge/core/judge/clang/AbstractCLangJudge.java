package com.sschakraborty.platform.kjudge.core.judge.clang;

import com.sschakraborty.platform.kjudge.core.AbstractJudge;
import com.sschakraborty.platform.kjudge.core.exec.ProcessUtility;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;

public abstract class AbstractCLangJudge extends AbstractJudge {
	public AbstractCLangJudge() throws AbstractBusinessException {
		super();
	}

	@Override
	protected void checkEnvironmentReady() throws AbstractBusinessException {
		String output = ProcessUtility.executeSystemCommand("gcc --version");
		if (!output.startsWith("gcc ")) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.JUDGE_ENVIRONMENT_NOT_READY,
				"C (gcc) environment is not ready or missing!"
			);
		}
	}
}