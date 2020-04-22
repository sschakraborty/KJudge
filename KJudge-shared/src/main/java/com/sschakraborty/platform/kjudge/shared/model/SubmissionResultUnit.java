package com.sschakraborty.platform.kjudge.shared.model;

public class SubmissionResultUnit {
	private Testcase testcase;
	private int timeRequired;
	private OutputCode outputCode;
	private String outputString;

	public Testcase getTestcase() {
		return testcase;
	}

	public void setTestcase(Testcase testcase) {
		this.testcase = testcase;
	}

	public int getTimeRequired() {
		return timeRequired;
	}

	public void setTimeRequired(int timeRequired) {
		this.timeRequired = timeRequired;
	}

	public OutputCode getOutputCode() {
		return outputCode;
	}

	public void setOutputCode(OutputCode outputCode) {
		this.outputCode = outputCode;
	}

	public String getOutputString() {
		return outputString;
	}

	public void setOutputString(String outputString) {
		this.outputString = outputString;
	}
}