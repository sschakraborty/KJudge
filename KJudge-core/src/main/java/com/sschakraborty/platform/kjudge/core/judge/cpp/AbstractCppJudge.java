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
	protected void checkPropertiesPresent() throws AbstractBusinessException {
		final String[] keyNames = {
			"cpp.basePath",
			"cpp.compiler",
			"cpp.versionSwitch",
			"cpp.versionString"
		};

		for (String key : keyNames) {
			if (this.getProperties().getProperty(key) == null) {
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.JUDGE_PROPERTIES_ERROR,
					String.format(
						"C++ judge property (%s) is missing from config (.properties) file!",
						key
					)
				);
			}
		}
	}

	@Override
	protected void checkEnvironmentReady() throws AbstractBusinessException {
		String basePath = this.getProperties().getProperty("cpp.basePath");
		String compiler = this.getProperties().getProperty("cpp.compiler");
		String versionSwitch = this.getProperties().getProperty("cpp.versionSwitch");
		String versionString = this.getProperties().getProperty("cpp.versionString");

		String output = ProcessUtility.executeSystemCommand(basePath + "/" + compiler + " " + versionSwitch);
		if (!output.trim().startsWith(versionString)) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.JUDGE_ENVIRONMENT_NOT_READY,
				"C++ (g++) environment is not ready or missing!"
			);
		}
	}
}