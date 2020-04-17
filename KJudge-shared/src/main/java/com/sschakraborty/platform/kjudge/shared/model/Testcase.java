package com.sschakraborty.platform.kjudge.shared.model;

public class Testcase {
	private String name;
	private String inputFilePath;
	private String expectedOutputFilePath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInputFilePath() {
		return inputFilePath;
	}

	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}

	public String getExpectedOutputFilePath() {
		return expectedOutputFilePath;
	}

	public void setExpectedOutputFilePath(String expectedOutputFilePath) {
		this.expectedOutputFilePath = expectedOutputFilePath;
	}
}