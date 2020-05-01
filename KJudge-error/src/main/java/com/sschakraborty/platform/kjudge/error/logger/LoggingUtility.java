package com.sschakraborty.platform.kjudge.error.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingUtility {
	private static final Logger CONSOLE_LOGGER;
	private static final Logger FILE_LOGGER;

	static {
		CONSOLE_LOGGER = LogManager.getLogger("KJudge.CONSOLE_LOGGER");
		FILE_LOGGER = LogManager.getLogger("KJudge.FILE_LOGGER");
	}

	public static Logger consoleLogger() {
		return CONSOLE_LOGGER;
	}

	public static Logger fileLogger() {
		return FILE_LOGGER;
	}

	public static Logger logger() {
		return FILE_LOGGER;
	}
}