package com.sschakraborty.platform.kjudge.core;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
@Ignore
public class JudgeEntryPointTest {
	@Test
	public void performJudgement() throws AbstractBusinessException {
		JudgeEntryPoint entryPoint = new JudgeEntryPoint();
		Assert.assertTrue(entryPoint != null);
	}
}