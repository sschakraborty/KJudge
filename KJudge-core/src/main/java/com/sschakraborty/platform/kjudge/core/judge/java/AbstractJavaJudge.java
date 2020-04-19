package com.sschakraborty.platform.kjudge.core.judge.java;

import com.sschakraborty.platform.kjudge.core.AbstractJudge;
import com.sschakraborty.platform.kjudge.core.exec.ProcessUtility;
import com.sschakraborty.platform.kjudge.core.exec.stageExecutor.CompileStageExecutor;
import com.sschakraborty.platform.kjudge.core.exec.stageExecutor.RunStageExecutor;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;
import com.sschakraborty.platform.kjudge.shared.model.Submission;
import com.sschakraborty.platform.kjudge.shared.model.Testcase;

import java.io.File;

public abstract class AbstractJavaJudge extends AbstractJudge {
	public AbstractJavaJudge() throws AbstractBusinessException {
		super();
	}

	final void compileProgram(String baseDirectory, String fileName) throws AbstractBusinessException {
		final CompileStageExecutor executor = new CompileStageExecutor(
			getProperties().getProperty("jvm.basePath") + "/bin",
			getProperties().getProperty("jvm.compiler"),
			baseDirectory,
			true
		);
		executor.setExpectedOutput("");
		executor.setArguments("-O", fileName);
		executor.execute();
	}

	final String getMainClassName(String baseDir) throws AbstractBusinessException {
		File baseDirectory = new File(baseDir);
		String basePath = this.getProperties().getProperty("jvm.basePath");
		String disassembler = getProperties().getProperty("jvm.disassembler");
		String[] classFileNames = baseDirectory.list(
			(file, fileName) -> fileName.endsWith(".class")
		);

		for (String fileName : classFileNames) {
			String output = ProcessUtility.executeSystemCommand(
				baseDirectory,
				basePath + "/bin/" + disassembler + " -public " + fileName
			);
			if (output.contains("public static void main(java.lang.String[]);")) {
				return fileName.substring(0, fileName.indexOf(".class"));
			}
		}
		ExceptionUtility.throwGenericException(
			JudgeErrorCode.COMPILATION_ERROR,
			"None of the (Java) classes contain main method!"
		);
		return null;
	}

	final void runProgram(
		Submission submission,
		Testcase testcase,
		String mainClassName,
		String baseDirectory,
		int timeLimit
	) throws AbstractBusinessException {
		final String runId = (
			submission.getSubmitter().getPrincipal()
				+ "_" + submission.getId() + "_"
				+ testcase.getName()
		);
		String basePath = this.getProperties().getProperty("jvm.basePath");
		String runtime = getProperties().getProperty("jvm.runtime");

		RunStageExecutor stageExecutor = new RunStageExecutor(
			runId,
			basePath + "/bin",
			runtime,
			baseDirectory
		);

		stageExecutor.setArguments("-server", mainClassName);
		stageExecutor.setTimeLimit(timeLimit);
		stageExecutor.setInputFilePath(testcase.getInputFilePath());
		stageExecutor.execute();
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