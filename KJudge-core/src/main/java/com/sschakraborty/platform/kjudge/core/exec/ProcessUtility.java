package com.sschakraborty.platform.kjudge.core.exec;

import com.sschakraborty.platform.kjudge.core.io.IOUtility;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ProcessUtility {
	private static final String ERR_EXEC_SYSTEM_COMMAND = "Error occurred while executing a system command (%s): %s";
	private static final String ERR_EXEC_PROCESS = "Error occurred while executing a process: %s";
	private static final String ERR_CTLE = "Compilation time limit exceeded for command (%s)";
	private static final String ERR_INTERRUPTED = "Compilation thread (%s) interrupted: %s!";
	private static final int WAIT_TIME = 8000;

	public static String executeSystemCommand(String systemCommand) throws AbstractBusinessException {
		try {
			ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.command(systemCommand.split(" "));
			processBuilder.redirectErrorStream(true);
			Process process = processBuilder.start();

			process.waitFor(WAIT_TIME, TimeUnit.MILLISECONDS);
			if (process.isAlive()) {
				process.destroyForcibly();
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.COMPILATION_TIME_OUT,
					String.format(ERR_CTLE, systemCommand)
				);
			}

			return collectOutput(process);
		} catch (IOException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_COMMAND_EXEC,
				String.format(ERR_EXEC_SYSTEM_COMMAND, systemCommand, e.getMessage())
			);
		} catch (InterruptedException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.THREAD_INTERRUPTED,
				String.format(
					ERR_INTERRUPTED,
					Thread.currentThread().getName(),
					e.getMessage()
				)
			);
		}
		return null;
	}

	public static String collectOutput(Process process) throws AbstractBusinessException {
		String temp;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = IOUtility.wrapReader(process.getInputStream());
		try {
			while ((temp = bufferedReader.readLine()) != null && temp.length() > 0) {
				stringBuilder.append(temp).append("\n");
			}
			bufferedReader.close();
		} catch (IOException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(ERR_EXEC_PROCESS, e.getMessage())
			);
		}
		return stringBuilder.toString();
	}
}