package com.sschakraborty.platform.kjudge.core;

import com.sschakraborty.platform.kjudge.core.io.IOUtility;
import com.sschakraborty.platform.kjudge.core.io.PropertyFileReader;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.shared.model.Submission;

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