package com.sschakraborty.platform.kjudge.error;

class ExceptionMessageBuilder {
	private static final StringBuffer STRING_BUFFER = new StringBuffer();

	private static void clearBuffer() {
		STRING_BUFFER.delete(0, STRING_BUFFER.length());
	}

	private static String extractDisplayMessage(AbstractBusinessException exception) {
		StringBuilder stringBuilder = new StringBuilder();
		for (StackTraceElement element : exception.getStackTrace()) {
			stringBuilder.append(">>> ").append(element.toString()).append("\n");
		}
		return stringBuilder.toString();
	}

	static String buildMessage(AbstractBusinessException exception) {
		clearBuffer();
		Class<? extends AbstractBusinessException> clazz = exception.getClass();

		STRING_BUFFER.append("-------------------------------- ERROR REPORT START --------------------------------").append("\n\n");
		STRING_BUFFER.append("---------------------------------- SUMMARY HEADER ----------------------------------").append("\n");
		STRING_BUFFER.append(">>> Error Code: ").append(exception.getErrorCode()).append("\n");
		STRING_BUFFER.append(">>> Error Message: ").append(exception.getErrorCode().getMessage()).append("\n");
		STRING_BUFFER.append(">>> Exception Class: ").append(clazz.getName()).append("\n");
		STRING_BUFFER.append(">>> Cause: ").append(exception.getMessage()).append("\n\n");
		STRING_BUFFER.append("------------------------------ ERROR STACK TRACE DUMP ------------------------------").append("\n");
		STRING_BUFFER.append(extractDisplayMessage(exception)).append("\n");
		STRING_BUFFER.append("--------------------------------- ERROR REPORT END ---------------------------------").append("\n");

		return STRING_BUFFER.toString();
	}
}
