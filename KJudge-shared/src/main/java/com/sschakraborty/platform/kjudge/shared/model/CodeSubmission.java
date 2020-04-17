package com.sschakraborty.platform.kjudge.shared.model;

public class CodeSubmission {
	private Language language;
	private String sourceCode;

	public CodeSubmission() {
		this.language = null;
		this.sourceCode = null;
	}

	public CodeSubmission(Language language, String sourceCode) {
		this.language = language;
		this.sourceCode = sourceCode;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
}