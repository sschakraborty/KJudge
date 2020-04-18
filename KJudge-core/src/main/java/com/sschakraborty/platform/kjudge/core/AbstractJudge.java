package com.sschakraborty.platform.kjudge.core;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;

public abstract class AbstractJudge implements Judge {
	public AbstractJudge() throws AbstractBusinessException {
		this.checkEnvironmentReady();
	}

	protected abstract void checkEnvironmentReady() throws AbstractBusinessException;
}