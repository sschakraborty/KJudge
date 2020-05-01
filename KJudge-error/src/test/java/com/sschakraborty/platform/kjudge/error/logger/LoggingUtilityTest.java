package com.sschakraborty.platform.kjudge.error.logger;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.StandardErrorCode;
import org.junit.Test;

public class LoggingUtilityTest {
	@Test
	public void testLogging() {
		LoggingUtility.consoleLogger().info("Hello World!");
		LoggingUtility.consoleLogger().error("Hello World!");
		LoggingUtility.consoleLogger().trace("Hello World!");
		LoggingUtility.consoleLogger().debug("Hello World!");
		LoggingUtility.consoleLogger().fatal("Hello World!");

		try {
			ExceptionUtility.throwGenericException(
				StandardErrorCode.INITIALIZATION_ERROR,
				"Custom exception message"
			);
		} catch (AbstractBusinessException e) {
			LoggingUtility.logger().error(e);
		}
	}
}