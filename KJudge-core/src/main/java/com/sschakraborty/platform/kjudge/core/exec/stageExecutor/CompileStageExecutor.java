package com.sschakraborty.platform.kjudge.core.exec.stageExecutor;

import com.sschakraborty.platform.kjudge.core.exec.ProcessUtility;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;

public class CompileStageExecutor implements StageExecutor {
	private final String compilerBasePath;
	private final String compilerProgram;
	private final boolean compareOutput;
	private String expectedOutput;
	private String[] arguments;

	public CompileStageExecutor(String compilerBasePath, String compilerProgram, boolean compareOutput) {
		this.compilerBasePath = compilerBasePath;
		this.compilerProgram = compilerProgram;
		this.compareOutput = compareOutput;
	}

	public String getCompilerBasePath() {
		return compilerBasePath;
	}

	public String getCompilerProgram() {
		return compilerProgram;
	}

	public String[] getArguments() {
		return arguments;
	}

	public void setArguments(String... arguments) {
		this.arguments = arguments;
	}

	public boolean isCompareOutput() {
		return compareOutput;
	}

	public String getExpectedOutput() {
		return expectedOutput;
	}

	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

	@Override
	public void execute() throws AbstractBusinessException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(this.compilerBasePath).append("/");
		stringBuilder.append(this.compilerProgram);
		stringBuilder.append(" ");

		for (String argument : this.arguments) {
			stringBuilder.append(argument).append(" ");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);

		String output = ProcessUtility.executeSystemCommand(stringBuilder.toString());
		if (this.compareOutput && !output.equals(this.expectedOutput)) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.COMPILATION_ERROR,
				String.format(
					"Compilation error for %s at %s!",
					this.compilerProgram,
					this.compilerBasePath
				)
			);
		}
	}
}