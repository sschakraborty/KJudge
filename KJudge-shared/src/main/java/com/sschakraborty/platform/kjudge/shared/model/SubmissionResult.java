package com.sschakraborty.platform.kjudge.shared.model;

import java.util.List;

public class SubmissionResult {
	private Problem problem;
	private Submission submission;

	private List<SubmissionResultUnit> resultUnits;

	public List<SubmissionResultUnit> getResultUnits() {
		return resultUnits;
	}

	public void setResultUnits(List<SubmissionResultUnit> resultUnits) {
		this.resultUnits = resultUnits;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Submission getSubmission() {
		return submission;
	}

	public void setSubmission(Submission submission) {
		this.submission = submission;
	}
}