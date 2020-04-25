package com.sschakraborty.platform.kjudge.shared.model;

import javax.persistence.*;

@Entity(name = "CodeSubmission")
@Table(name = "CODE_SUBMISSION")
public class CodeSubmission {
	@Id
	@SequenceGenerator(
		name = "CODE_SUBMISSION_SEQ",
		sequenceName = "CODE_SUBMISSION_SEQ",
		allocationSize = 1
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "CODE_SUBMISSION_SEQ"
	)
	private long id;

	@Column(
		name = "LANGUAGE",
		nullable = false
	)
	private Language language;

	@Lob
	@Column(
		name = "SOURCE_CODE",
		nullable = false
	)
	private String sourceCode;

	public CodeSubmission() {
		this.language = null;
		this.sourceCode = null;
	}

	public CodeSubmission(Language language, String sourceCode) {
		this.language = language;
		this.sourceCode = sourceCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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