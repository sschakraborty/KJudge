package com.sschakraborty.platform.kjudge.shared.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SUBMISSION_RESULT")
public class SubmissionResult {
	@Id
	@SequenceGenerator(
		name = "SUB_RES_SEQ_GEN",
		sequenceName = "SUB_RES_SEQ_GEN",
		allocationSize = 1
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "SUB_RES_SEQ_GEN"
	)
	@Column(
		name = "ID"
	)
	private long id;

	private Submission submission;

	@Column(
		name = "COMPILATION_ERROR",
		nullable = false
	)
	private boolean compilationError;

	@Lob
	@Column(
		name = "COMPILATION_ERR_MSG"
	)
	private String compilationErrorMessage;

	@Column(
		name = "OUTPUT_CODE",
		nullable = false
	)
	@Enumerated(EnumType.STRING)
	private OutputCode outputCode;

	@JoinTable(
		name = "SUBMISSION_RES_UNIT_RELATION"
	)
	@OneToMany(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private List<SubmissionResultUnit> resultUnits;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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