package com.sschakraborty.platform.kjudge.shared.model;

import java.time.LocalDateTime;

public class Submission {
	private Problem problem;
	private CodeSubmission codeSubmission;
	private SubmissionResult submissionResult;
	private LocalDateTime dateTime;

	public Submission() {
		this.problem = null;
		this.codeSubmission = null;
		this.submissionResult = null;
		this.dateTime = null;
	}

	public Submission(Problem problem, CodeSubmission codeSubmission, SubmissionResult submissionResult) {
		this.problem = problem;
		this.codeSubmission = codeSubmission;
		this.submissionResult = submissionResult;
		this.dateTime = LocalDateTime.now();
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public CodeSubmission getCodeSubmission() {
		return codeSubmission;
	}

	public void setCodeSubmission(CodeSubmission codeSubmission) {
		this.codeSubmission = codeSubmission;
	}

	public SubmissionResult getSubmissionResult() {
		return submissionResult;
	}

	public void setSubmissionResult(SubmissionResult submissionResult) {
		this.submissionResult = submissionResult;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public boolean isEvaluated() {
		return this.submissionResult != null;
	}

	public void updateToCurrentTimestamp() {
		this.dateTime = LocalDateTime.now();
	}
}