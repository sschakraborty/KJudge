package com.sschakraborty.platform.kjudge.core.exec.stageExecutor;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;

public interface StageExecutor {
	void execute() throws AbstractBusinessException;
}