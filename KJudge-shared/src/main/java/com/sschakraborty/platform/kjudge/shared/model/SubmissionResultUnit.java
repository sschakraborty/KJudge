package com.sschakraborty.platform.kjudge.shared.model;

import javax.persistence.*;

@Entity
@Table(name = "SUBMISSION_RESULT_UNIT")
public class SubmissionResultUnit {
	@Id
	@SequenceGenerator(
		name = "SUB_RES_UNIT_SEQ_GEN",
		sequenceName = "SUB_RES_UNIT_SEQ_GEN",
		allocationSize = 1
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "SUB_RES_UNIT_SEQ_GEN"
	)
	@Column(
		name = "ID"
	)
	private long id;

	@JoinColumn(
		name = "TESTCASE_ID"
	)
	@ManyToOne(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private Testcase testcase;

	@Column(
		name = "TIME_REQUIRED"
	)
	private int timeRequired;

	@Column(
		name = "OUTPUT_CODE",
		nullable = false
	)
	@Enumerated(EnumType.STRING)
	private OutputCode outputCode;

	@Lob
	@Column(
		name = "OUTPUT_STRING"
	)
	private String outputString;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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