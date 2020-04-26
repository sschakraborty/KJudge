package com.sschakraborty.platform.kjudge.shared.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SUBMISSION")
public class Submission {
	@Id
	@SequenceGenerator(
		name = "SUBMISSION_SEQ_GEN",
		sequenceName = "SUBMISSION_SEQ_GEN",
		allocationSize = 1
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "SUBMISSION_SEQ_GEN"
	)
	@Column(
		name = "ID"
	)
	private long id;

	@JoinColumn(
		name = "PROBLEM_HANDLE",
		nullable = false
	)
	@ManyToOne(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private Problem problem;

	@JoinColumn(
		name = "ID",
		nullable = false
	)
	@OneToOne(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	@MapsId
	private CodeSubmission codeSubmission;

	@JoinColumn(
		name = "SUBMISSION_RESULT_ID",
		nullable = false
	)
	@OneToOne(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private SubmissionResult submissionResult;

	@Column(
		name = "SUBMISSION_DT",
		nullable = false
	)
	private LocalDateTime dateTime;

	@JoinColumn(
		name = "SUBMITTER_PRINCIPAL",
		nullable = false
	)
	@ManyToOne(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private User submitter;

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

	public User getSubmitter() {
		return submitter;
	}

	public void setSubmitter(User submitter) {
		this.submitter = submitter;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}