package com.sschakraborty.platform.kjudge.error;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionMessageBuilder {
	private static final StringBuffer STRING_BUFFER = new StringBuffer();

	private static void clearBuffer() {
		STRING_BUFFER.delete(0, STRING_BUFFER.length());
	}

	private static String extractStackTrace(AbstractBusinessException exception) {
		final StringWriter stringWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stringWriter);
		exception.printStackTrace(printWriter);
		return stringWriter.toString();
	}

	public static String buildMessage(AbstractBusinessException exception) {
		clearBuffer();
		Class<? extends AbstractBusinessException> clazz = exception.getClass();

		STRING_BUFFER.append("-------------------------------------------------------------").append("\n");
		STRING_BUFFER.append("Error Code: ").append(exception.getErrorCode()).append("\n");
		STRING_BUFFER.append("Error Message: ").append(exception.getErrorCode().getMessage()).append("\n");
		STRING_BUFFER.append("Exception Class: ").append(clazz.getName()).append("\n");
		STRING_BUFFER.append("Cause: ").append(exception.getMessage()).append("\n");
		STRING_BUFFER.append("-------------------------------------------------------------").append("\n");
		STRING_BUFFER.append(extractStackTrace(exception)).append("\n");

		return STRING_BUFFER.toString();
	}
}
