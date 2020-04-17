package com.sschakraborty.platform.kjudge.shared.model;

import java.util.Map;

/*
 * IO output size is in kilobytes (kB)
 */
public class IOConstraint {
	private Map<Language, Integer> outputLimits;

	public Map<Language, Integer> getOutputLimits() {
		return outputLimits;
	}

	public void setOutputLimits(Map<Language, Integer> outputLimits) {
		this.outputLimits = outputLimits;
	}
}