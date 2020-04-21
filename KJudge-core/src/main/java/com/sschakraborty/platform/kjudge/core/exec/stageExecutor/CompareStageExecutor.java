package com.sschakraborty.platform.kjudge.core.exec.stageExecutor;

import com.sschakraborty.platform.kjudge.core.io.IOUtility;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;

import java.io.*;

public class CompareStageExecutor extends AbstractStageExecutor {
	private final File actualOutputFile;
	private final File expectedOutputFile;

	public CompareStageExecutor(String baseDirectory, String actualFileName, String expectedOutputFilePath)
		throws AbstractBusinessException {
		super(baseDirectory);
		this.actualOutputFile = this.fetchFile(baseDirectory + "/" + actualFileName);
		this.expectedOutputFile = this.fetchFile(expectedOutputFilePath);
	}

	private File fetchFile(String filePath) throws AbstractBusinessException {
		File file = new File(filePath);

		if (!file.exists()) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"Provided file (%s) in compare stage does not exist!",
					filePath
				)
			);
		}

		if (!file.canRead()) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"Provided file (%s) in compare stage does not have read access!",
					filePath
				)
			);
		}

		if (!file.isFile()) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"Provided path (%s) in compare stage is not a file!",
					filePath
				)
			);
		}

		return file;
	}

	@Override
	public void execute() throws AbstractBusinessException {
		BufferedReader actualOutputReader = null;
		BufferedReader expectedOutputReader = null;

		try {
			actualOutputReader = IOUtility.wrapReader(new FileInputStream(this.actualOutputFile));
		} catch (FileNotFoundException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"Provided file (%s) in compare stage does not exist!",
					this.actualOutputFile.getAbsolutePath()
				)
			);
		}

		try {
			expectedOutputReader = IOUtility.wrapReader(new FileInputStream(this.expectedOutputFile));
		} catch (FileNotFoundException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"Provided file (%s) in compare stage does not exist!",
					this.expectedOutputFile.getAbsolutePath()
				)
			);
		}

		try {
			this.compareContents(actualOutputReader, expectedOutputReader);
		} finally {
			try {
				actualOutputReader.close();
			} catch (IOException e) {
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
					String.format(
						"Provided file (%s) stream failed to close!",
						this.actualOutputFile.getAbsolutePath()
					)
				);
			}

			try {
				expectedOutputReader.close();
			} catch (IOException e) {
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
					String.format(
						"Provided file (%s) stream failed to close!",
						this.expectedOutputFile.getAbsolutePath()
					)
				);
			}
		}
	}

	private void compareContents(BufferedReader actualOutputReader, BufferedReader expectedOutputReader)
		throws AbstractBusinessException {

		String expectedOutputLine, actualOutputLine;
		try {
			while (
				(expectedOutputLine = expectedOutputReader.readLine()) != null &&
					(actualOutputLine = actualOutputReader.readLine()) != null
			) {
				this.compareLine(actualOutputLine, expectedOutputLine);
			}

			while ((expectedOutputLine = expectedOutputReader.readLine()) != null) {
				if (expectedOutputLine.trim().length() > 0) {
					ExceptionUtility.throwGenericException(
						JudgeErrorCode.WRONG_ANSWER_ERROR,
						"Actual output of the solution is smaller than the expected output!"
					);
				}
			}

			while ((actualOutputLine = actualOutputReader.readLine()) != null) {
				if (actualOutputLine.trim().length() > 0) {
					ExceptionUtility.throwGenericException(
						JudgeErrorCode.WRONG_ANSWER_ERROR,
						"Actual output has more content than expected output!"
					);
				}
			}
		} catch (IOException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"Error while reading from files (%s) and/or (%s): %s",
					this.actualOutputFile.getAbsolutePath(),
					this.expectedOutputFile.getAbsolutePath(),
					e.getMessage()
				)
			);
		}
	}

	private void compareLine(String firstLine, String secondLine) throws AbstractBusinessException {
		firstLine = firstLine.trim();
		secondLine = secondLine.trim();

		if (!firstLine.equals(secondLine)) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.WRONG_ANSWER_ERROR,
				"Actual output of the solution does not match with the expected output."
			);
		}
	}
}
