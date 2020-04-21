package com.sschakraborty.platform.kjudge.core.exec.stageExecutor;

import com.sschakraborty.platform.kjudge.core.io.IOUtility;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RunStageExecutor extends AbstractStageExecutor {
	private final String runId;
	private final String runBasePath;
	private final String runProgram;
	private String[] arguments;
	private int timeLimit;
	private int memoryLimit;
	private int ioLimit;

	private String inputFilePath;
	private String errOutput;

	public RunStageExecutor(String runId, String runBasePath, String runProgram, String baseDirectory) {
		super(baseDirectory);
		this.runId = runId;
		this.runBasePath = runBasePath;
		this.runProgram = runProgram;
		this.arguments = new String[0];
	}

	public String getRunId() {
		return runId;
	}

	public String getRunBasePath() {
		return runBasePath;
	}

	public String getRunProgram() {
		return runProgram;
	}

	public String[] getArguments() {
		return arguments;
	}

	public void setArguments(String... arguments) {
		this.arguments = arguments;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public int getMemoryLimit() {
		return memoryLimit;
	}

	public void setMemoryLimit(int memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	public int getIoLimit() {
		return ioLimit;
	}

	public void setIoLimit(int ioLimit) {
		this.ioLimit = ioLimit;
	}

	public String getInputFilePath() {
		return inputFilePath;
	}

	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}

	public String getErrOutput() {
		return errOutput;
	}

	public void setErrOutput(String errOutput) {
		this.errOutput = errOutput;
	}

	@Override
	public void execute() throws AbstractBusinessException {
		final ProcessBuilder processBuilder = constructProcessBuilder();
		this.errOutput = performExecution(processBuilder);
	}

	private String performExecution(ProcessBuilder processBuilder) throws AbstractBusinessException {
		StringBuilder error = new StringBuilder();
		try {
			Process process = processBuilder.start();
			process.waitFor(this.timeLimit, TimeUnit.MILLISECONDS);

			if (process.isAlive()) {
				process.destroyForcibly();
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.RUN_TIME_LIMIT_EXCEEDED,
					String.format(
						"Time limit exceeded for RunId (%s)!",
						this.runId
					)
				);
			}

			String temp;
			final BufferedReader bufferedReader = IOUtility.wrapReader(process.getErrorStream());
			while ((temp = bufferedReader.readLine()) != null && temp.length() > 0) {
				error.append(temp).append("\n");
			}
			bufferedReader.close();
		} catch (IOException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"IO error while judging: %s",
					e.getMessage()
				)
			);
		} catch (InterruptedException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.THREAD_INTERRUPTED,
				String.format(
					"Thread (%s) interrupted while judging code: %s",
					Thread.currentThread().getName(),
					e.getMessage()
				)
			);
		}
		return error.toString();
	}

	private ProcessBuilder constructProcessBuilder() throws AbstractBusinessException {
		final File directory = constructBaseDirectory();

		final ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.redirectErrorStream(false);
		processBuilder.directory(directory);
		processBuilder.command(constructCommandList());
		processBuilder.redirectInput(constructInputFile());
		processBuilder.redirectOutput(constructOutputFile());

		return processBuilder;
	}


	private List<String> constructCommandList() {
		final List<String> commandList = new ArrayList<>();
		commandList.add(this.runBasePath + "/" + this.runProgram);
		commandList.addAll(Arrays.asList(arguments));
		return commandList;
	}

	private File constructInputFile() throws AbstractBusinessException {
		final File inputFile = new File(this.inputFilePath);

		if (!inputFile.exists()) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"Provided input file (%s) in run stage does not exist!",
					this.inputFilePath
				)
			);
		}

		if (!inputFile.isFile() || !inputFile.canRead()) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"Provided input file (%s) in run stage is either not a file or not readable!",
					this.inputFilePath
				)
			);
		}

		return inputFile;
	}

	private File constructOutputFile() throws AbstractBusinessException {
		final File outputFile = new File(getBaseDirectory() + "/" + this.runId);

		if (outputFile.exists()) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"Output file (%s) in run stage already exists!",
					outputFile.getAbsolutePath()
				)
			);
		} else {
			try {
				outputFile.createNewFile();
			} catch (IOException e) {
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
					String.format(
						"Output file (%s) in run stage could not be created!",
						outputFile.getAbsolutePath()
					)
				);
			}
		}

		if (!outputFile.canWrite()) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"Output file (%s) in run stage is not writable!",
					outputFile.getAbsolutePath()
				)
			);
		}

		return outputFile;
	}
}
