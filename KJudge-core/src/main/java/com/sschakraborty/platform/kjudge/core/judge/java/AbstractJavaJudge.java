package com.sschakraborty.platform.kjudge.core.judge.java;

import com.sschakraborty.platform.kjudge.core.AbstractJudge;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;

public abstract class AbstractJavaJudge extends AbstractJudge {
	public AbstractJavaJudge() throws AbstractBusinessException {
		super();
	}

	@Override
	protected void checkPropertiesPresent() throws AbstractBusinessException {
		final String[] keyNames = {
			"jvm.basePath",
			"jvm.compiler",
			"jvm.compiler.versionSwitch",
			"jvm.compiler.versionString",
			"jvm.compiler.args",
			"jvm.disassembler",
			"jvm.runtime",
			"jvm.runtime.args"
		};

		for (String key : keyNames) {
			if (this.getProperties().getProperty(key) == null) {
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.JUDGE_PROPERTIES_ERROR,
					String.format(
						"Java judge property (%s) is missing from config (.properties) file!",
						key
					)
				);
			}
		}
	}
}