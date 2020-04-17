package com.sschakraborty.platform.kjudge.shared.model;

import java.util.List;

public class Problem {
	private String problemHandle;
	private CodingEvent codingEvent;
	private String name;
	private String description;
	private List<Testcase> testcases;
	private List<CodeSubmission> solutions;
	private MemoryConstraint memoryConstraint;
	private TimeConstraint timeConstraint;
	private IOConstraint ioConstraint;
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