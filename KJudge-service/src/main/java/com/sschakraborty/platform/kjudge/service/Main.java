package com.sschakraborty.platform.kjudge.service;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.logger.LoggingUtility;
import io.vertx.core.VertxOptions;

public class Main {
	public static void main(String[] args) {
		LoggingUtility.logger().info("Starting KJudge!");
		final VertxOptions vertxOptions = buildVertxOptions();
		try {
			final int portNumber = 12400;
			final Application application = new Application(portNumber, vertxOptions);
			application.init();
			LoggingUtility.logger().info("KJudge started successfully!");
			LoggingUtility.consoleLogger().info("<----- [STARTED KSystem Judge] ----->");
		} catch (AbstractBusinessException e) {
			LoggingUtility.logger().error(e);
		}
	}

	private static VertxOptions buildVertxOptions() {
		final VertxOptions vertxOptions = new VertxOptions();
		vertxOptions.getFileSystemOptions().setFileCachingEnabled(false);
		return vertxOptions;
	}
}