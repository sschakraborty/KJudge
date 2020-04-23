package com.sschakraborty.platform.kjudge.shared.model;

import java.util.List;

public class SubmissionResult {
	private Submission submission;
	private boolean compilationError;
	private String compilationErrorMessage;
	private OutputCode outputCode;

	private List<SubmissionResultUnit> resultUnits;

	public List<SubmissionResultUnit> getResultUnits() {
		return resultUnits;
	}

	public void setResultUnits(List<SubmissionResultUnit> resultUnits) {
		this.resultUnits = resultUnits;
	}

	public Submission getSubmission() {
		return submission;
	}

	public void setSubmission(Submission submission) {
		this.submission = submission;
	}

	public boolean isCompilationError() {
		return compilationError;
	}

	public void setCompilationError(boolean compilationError) {
		this.compilationError = compilationError;
	}

	public String getCompilationErrorMessage() {
		return compilationErrorMessage;
	}

	public void setCompilationErrorMessage(String compilationErrorMessage) {
		this.compilationErrorMessage = compilationErrorMessage;
	}

	public OutputCode getOutputCode() {
		return outputCode;
	}

	public void setOutputCode(OutputCode outputCode) {
		this.outputCode = outputCode;
	}
}