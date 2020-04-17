package com.sschakraborty.platform.kjudge.shared.model;

import java.util.Map;

/*
 * Memory limit is in kilobytes (kB)
 */
public class MemoryConstraint {
	private Map<Language, Integer> memoryConstraints;

	public Map<Language, Integer> getMemoryConstraints() {
		return memoryConstraints;
	}

	public void setMemoryConstraints(Map<Language, Integer> memoryConstraints) {
		this.memoryConstraints = memoryConstraints;
	}
}