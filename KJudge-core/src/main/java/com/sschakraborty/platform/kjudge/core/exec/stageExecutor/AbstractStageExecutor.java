package com.sschakraborty.platform.kjudge.core.exec.stageExecutor;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;

import java.io.File;

public abstract class AbstractStageExecutor implements StageExecutor {
	private final String baseDirectory;

	public AbstractStageExecutor(String baseDirectory) {
		this.baseDirectory = baseDirectory;
	}

	public String getBaseDirectory() {
		return baseDirectory;
	}

	File constructBaseDirectory() throws AbstractBusinessException {
		final File directory = new File(this.baseDirectory);

		if (!directory.exists()) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"Provided directory (%s) in run stage does not exist!",
					this.baseDirectory
				)
			);
		}

		if (!directory.canRead() || !directory.canWrite()) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"Provided directory (%s) in run stage does not have read / write access!",
					this.baseDirectory
				)
			);
		}

		if (!directory.isDirectory()) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"Provided path (%s) in run stage is not a directory!",
					this.baseDirectory
				)
			);
		}

		return directory;
	}
}