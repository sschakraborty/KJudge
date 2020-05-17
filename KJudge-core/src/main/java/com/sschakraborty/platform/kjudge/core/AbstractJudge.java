package com.sschakraborty.platform.kjudge.core;

import com.sschakraborty.platform.kjudge.core.exec.stageExecutor.CompareStageExecutor;
import com.sschakraborty.platform.kjudge.core.io.IOUtility;
import com.sschakraborty.platform.kjudge.core.io.PropertyFileReader;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;
import com.sschakraborty.platform.kjudge.shared.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class AbstractJudge implements Judge {
	private final Properties masterProperties;
	private final Properties properties;
	private final PropertyFileReader propertyFileReader;
	private final String stageDirectory;

	public AbstractJudge() throws AbstractBusinessException {
		this.propertyFileReader = new PropertyFileReader();
		this.masterProperties = this.readMasterProperties();
		this.stageDirectory = this.masterProperties.getProperty("kjudge.stageDirectory");
		this.properties = this.readProperties();
		this.checkPropertiesPresent();
		this.checkEnvironmentReady();
	}

	private Properties readMasterProperties() throws AbstractBusinessException {
		return this.propertyFileReader.readPropertiesUsingFileName("kjudge.properties");
	}

	protected final String writeSubmissionToStageArea(Submission submission, String fileName) throws AbstractBusinessException {
		StringBuilder baseDirectoryPath = new StringBuilder();
		baseDirectoryPath.append(this.stageDirectory).append("/");
		baseDirectoryPath.append(submission.getProblem().getProblemHandle());
		baseDirectoryPath.append("/");
		baseDirectoryPath.append(submission.getSubmitter().getPrincipal());
		baseDirectoryPath.append("_").append(submission.getId());

		IOUtility.writeToFile(baseDirectoryPath.toString(), fileName, submission.getCodeSubmission().getSourceCode());
		return baseDirectoryPath.toString();
	}

	protected final void compareOutput(
		String baseDirectory,
		String fileName,
		String expectedOutputFilePath
	) throws AbstractBusinessException {
		CompareStageExecutor executor;
		executor = new CompareStageExecutor(baseDirectory, fileName, expectedOutputFilePath);
		executor.execute();
	}

	protected final void fillCompilationError(SubmissionResult submissionResult, AbstractBusinessException e) {
		if (e.getErrorCode() == JudgeErrorCode.COMPILATION_ERROR) {
			submissionResult.setCompilationError(true);
		}
		submissionResult.setOutputCode(OutputCode.mapOutputCode(e.getErrorCode()));
		submissionResult.setCompilationErrorMessage(e.getErrorDump());
	}

	protected final boolean fillRuntimeError(SubmissionResult submissionResult, Testcase testcase, AbstractBusinessException e) {
		SubmissionResultUnit submissionResultUnit = new SubmissionResultUnit();
		submissionResultUnit.setTestcase(testcase);
		submissionResultUnit.setOutputCode(OutputCode.mapOutputCode(e.getErrorCode()));
		submissionResultUnit.setOutputString(e.getErrorDump());

		putResultUnit(submissionResult, submissionResultUnit);
		return submissionResultUnit.getOutputCode() == OutputCode.INTERNAL_ERROR;
	}

	protected final void fillSuccess(SubmissionResult submissionResult, Testcase testcase) {
		SubmissionResultUnit submissionResultUnit = new SubmissionResultUnit();
		submissionResultUnit.setTestcase(testcase);
		submissionResultUnit.setOutputCode(OutputCode.ACCEPTED);
		submissionResultUnit.setOutputString("");
		putResultUnit(submissionResult, submissionResultUnit);
	}

	protected final SubmissionResult fetchSubmissionResult(Submission submission) {
		SubmissionResult submissionResult = submission.getSubmissionResult();
		if (submissionResult == null) {
			submissionResult = new SubmissionResult();
			submissionResult.setOutputCode(OutputCode.INTERNAL_ERROR);
			submission.setSubmissionResult(submissionResult);
			submissionResult.setSubmission(submission);
		}
		return submissionResult;
	}

	private void putResultUnit(SubmissionResult submissionResult, SubmissionResultUnit submissionResultUnit) {
		List<SubmissionResultUnit> submissionResultUnits = submissionResult.getResultUnits();
		if (submissionResultUnits == null) {
			submissionResultUnits = new ArrayList<>();
			submissionResult.setResultUnits(submissionResultUnits);
		}
		submissionResultUnits.add(submissionResultUnit);
	}

	protected abstract void checkPropertiesPresent() throws AbstractBusinessException;

	public Properties getProperties() {
		return properties;
	}

	public PropertyFileReader getPropertyFileReader() {
		return propertyFileReader;
	}

	public Properties getMasterProperties() {
		return masterProperties;
	}

	public String getStageDirectory() {
		return stageDirectory;
	}

	protected abstract Properties readProperties() throws AbstractBusinessException;

	protected abstract void checkEnvironmentReady() throws AbstractBusinessException;
}