package com.sschakraborty.platform.kjudge.shared.model;

import javax.persistence.*;

@Entity
@Table(name = "TESTCASE")
public class Testcase {
	@Id
	@SequenceGenerator(
		name = "TESTCASE_SEQ_GEN",
		sequenceName = "TESTCASE_SEQ_GEN",
		allocationSize = 1
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "TESTCASE_SEQ_GEN"
	)
	@Column(
		name = "ID"
	)
	private long id;

	@Column(
		name = "TESTCASE_NAME",
		length = 50,
		nullable = false
	)
	private String name;

	@Column(
		name = "INPUT_FILE",
		length = 512,
		nullable = false
	)
	private String inputFilePath;

	@Column(
		name = "EXPECTED_OUTPUT_FILE",
		length = 512,
		nullable = false
	)
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