package com.sschakraborty.platform.kjudge.shared.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PROBLEM")
public class Problem {
	@Id
	@Column(
		name = "PROBLEM_HANDLE",
		length = 25
	)
	private String problemHandle;

	@JoinColumn(
		name = "CODING_EVENT_HANDLE",
		nullable = false
	)
	@ManyToOne(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private CodingEvent codingEvent;

	@Column(
		name = "PROBLEM_NAME",
		nullable = false
	)
	private String name;

	@Lob
	@Column(
		name = "DESCRIPTION"
	)
	private String description;

	@JoinTable(
		name = "PROBLEM_TESTCASE_RELATION"
	)
	@OneToMany(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private List<Testcase> testcases;

	@JoinTable(
		name = "PROBLEM_SOLUTION_RELATION"
	)
	@OneToMany(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private List<CodeSubmission> solutions;

	@JoinColumn(
		name = "MEMORY_CONSTRAINT_ID",
		nullable = false
	)
	@ManyToOne(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private MemoryConstraint memoryConstraint;

	@JoinColumn(
		name = "TIME_CONSTRAINT_ID",
		nullable = false
	)
	@ManyToOne(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private TimeConstraint timeConstraint;

	@JoinColumn(
		name = "IO_CONSTRAINT_ID",
		nullable = false
	)
	@ManyToOne(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private IOConstraint ioConstraint;

	@JoinColumn(
		name = "CREATOR_USER_PRINCIPAL",
		nullable = false
	)
	@ManyToOne(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL
	)
	private User creator;

	public String getProblemHandle() {
		return problemHandle;
	}

	public void setProblemHandle(String problemHandle) {
		this.problemHandle = problemHandle;
	}

	public CodingEvent getCodingEvent() {
		return codingEvent;
	}

	public void setCodingEvent(CodingEvent codingEvent) {
		this.codingEvent = codingEvent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Testcase> getTestcases() {
		return testcases;
	}

	public void setTestcases(List<Testcase> testcases) {
		this.testcases = testcases;
	}

	public List<CodeSubmission> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<CodeSubmission> solutions) {
		this.solutions = solutions;
	}

	public MemoryConstraint getMemoryConstraint() {
		return memoryConstraint;
	}

	public void setMemoryConstraint(MemoryConstraint memoryConstraint) {
		this.memoryConstraint = memoryConstraint;
	}

	public TimeConstraint getTimeConstraint() {
		return timeConstraint;
	}

	public void setTimeConstraint(TimeConstraint timeConstraint) {
		this.timeConstraint = timeConstraint;
	}

	public IOConstraint getIoConstraint() {
		return ioConstraint;
	}

	public void setIoConstraint(IOConstraint ioConstraint) {
		this.ioConstraint = ioConstraint;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}
}