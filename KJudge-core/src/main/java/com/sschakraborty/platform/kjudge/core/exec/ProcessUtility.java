package com.sschakraborty.platform.kjudge.core.exec;

import com.sschakraborty.platform.kjudge.core.io.IOUtility;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;

import java.io.BufferedReader;
import java.io.IOException;

public class ProcessUtility {
	private static final String ERR_EXEC_SYSTEM_COMMAND = "Error occurred while executing a system command (%s): %s";
	private static final String ERR_EXEC_PROCESS = "Error occurred while executing a process: %s";

	public static String executeSystemCommand(String systemCommand) throws AbstractBusinessException {
		try {
			Process process = Runtime.getRuntime().exec(systemCommand);
			return collectOutput(process);
		} catch (IOException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_COMMAND_EXEC,
				String.format(ERR_EXEC_SYSTEM_COMMAND, systemCommand, e.getMessage())
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