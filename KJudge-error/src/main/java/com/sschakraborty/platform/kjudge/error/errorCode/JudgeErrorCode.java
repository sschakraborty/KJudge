package com.sschakraborty.platform.kjudge.error.errorCode;

public enum JudgeErrorCode implements ErrorCode {
	UNSUPPORTED_LANGUAGE("Unsupported Language!"),
	IO_ERROR_IN_COMMAND_EXEC("IO error while executing command!"),
	IO_ERROR_IN_PROCESS_EXEC("IO error while executing process!"),
	JUDGE_ENVIRONMENT_NOT_READY("Environment not ready or missing!"),
	JUDGE_ENTRY_LIST_ERROR("Judge entry list resource error!"),
	MALFORMED_JUDGE_CLASS("Judge class not defined according to specified rules!"),
	JUDGE_PROPERTIES_ERROR("Error in judge (.properties) config file!"),
	COMPILATION_ERROR("Compilation error!"),
	COMPILATION_TIME_OUT("Compilation time limit exceeded!"),
	THREAD_INTERRUPTED("Judge thread interrupted!"),
	RUN_TIME_LIMIT_EXCEEDED("Run time limit exceeded!");

	private final String message;

	JudgeErrorCode(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}