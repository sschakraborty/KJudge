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
	protected void checkPropertiesPresent() throws AbstractBusinessException {
		final String[] keyNames = {
			"clang.basePath",
			"clang.compiler",
			"clang.versionSwitch",
			"clang.versionString"
		};

		for (String key : keyNames) {
			if (this.getProperties().getProperty(key) == null) {
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.JUDGE_PROPERTIES_ERROR,
					String.format(
						"C judge property (%s) is missing from config (.properties) file!",
						key
					)
				);
			}
		}
	}

	@Override
	protected void checkEnvironmentReady() throws AbstractBusinessException {
		String basePath = this.getProperties().getProperty("clang.basePath");
		String compiler = this.getProperties().getProperty("clang.compiler");
		String versionSwitch = this.getProperties().getProperty("clang.versionSwitch");
		String versionString = this.getProperties().getProperty("clang.versionString");

		String output = ProcessUtility.executeSystemCommand(basePath + "/" + compiler + " " + versionSwitch);
		if (!output.trim().startsWith(versionString)) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.JUDGE_ENVIRONMENT_NOT_READY,
				"C (gcc) environment is not ready or missing!"
			);
		}
	}
}