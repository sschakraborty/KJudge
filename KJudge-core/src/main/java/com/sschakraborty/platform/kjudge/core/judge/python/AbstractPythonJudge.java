package com.sschakraborty.platform.kjudge.core.judge.python;

import com.sschakraborty.platform.kjudge.core.AbstractJudge;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;

public abstract class AbstractPythonJudge extends AbstractJudge {
	public AbstractPythonJudge() throws AbstractBusinessException {
		super();
	}

	@Override
	protected void checkPropertiesPresent() throws AbstractBusinessException {
		final String[] keyNames = {
			"python.basePath",
			"python.program",
			"python.versionSwitch",
			"python.versionString"
		};

		for (String key : keyNames) {
			if (this.getProperties().getProperty(key) == null) {
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.JUDGE_PROPERTIES_ERROR,
					String.format(
						"Python judge property (%s) is missing from config (.properties) file!",
						key
					)
				);
			}
		}
	}
}