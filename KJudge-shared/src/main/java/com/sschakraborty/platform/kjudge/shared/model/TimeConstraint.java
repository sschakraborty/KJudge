package com.sschakraborty.platform.kjudge.shared.model;

import java.util.Map;

/*
 * Time is in millisecond (ms)
 */
public class TimeConstraint {
	private Map<Language, Integer> timeConstraints;

	public Map<Language, Integer> getTimeConstraints() {
		return timeConstraints;
	}

	public void setTimeConstraints(Map<Language, Integer> timeConstraints) {
		this.timeConstraints = timeConstraints;
	}
}