package com.sschakraborty.platform.kjudge.core.io;

import com.sschakraborty.platform.kjudge.core.Judge;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JudgeListReader {
	private static final String PREFIX = "com.sschakraborty.platform.kjudge.core.judge.";
	private final BufferedReader bufferedReader;
	private final List<Class<? extends Judge>> activeJudges;
	private final List<Class<? extends Judge>> inactiveJudges;

	public JudgeListReader() throws AbstractBusinessException {
		InputStream inputStream = this.getResourceFile();
		this.bufferedReader = IOUtility.wrapReader(inputStream);
		this.activeJudges = new ArrayList<>();
		this.inactiveJudges = new ArrayList<>();
		this.readJudges();
		this.cleanUpRoutine();
	}

	private void cleanUpRoutine() throws AbstractBusinessException {
		try {
			this.bufferedReader.close();
		} catch (IOException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_PROCESS_EXEC,
				String.format(
					"Error while cleaning up judge list reader: %s",
					e.getMessage()
				)
			);
		}
	}

	private InputStream getResourceFile() throws AbstractBusinessException {
		InputStream inputStream = null;
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream("judges.list");
			if (inputStream.available() <= 0) {
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.JUDGE_ENTRY_LIST_ERROR,
					"Judge resource (judges.list) is empty!"
				);
			}
		} catch (IOException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.JUDGE_ENTRY_LIST_ERROR,
				"Judge resource (judges.list) missing!"
			);
		}
		return inputStream;
	}

	private void readJudges() throws AbstractBusinessException {
		String temp;
		Scanner scanner;
		try {
			while ((temp = this.bufferedReader.readLine()) != null && temp.length() > 0) {
				scanner = new Scanner(temp);
				String className = scanner.next();
				String switchInstruction = scanner.next();

				try {
					Class<? extends Judge> judge = (Class<? extends Judge>) Class.forName(PREFIX + className);
					this.appendToList(switchInstruction, judge);
				} catch (ClassNotFoundException | ClassCastException e) {
					ExceptionUtility.throwGenericException(
						JudgeErrorCode.JUDGE_ENTRY_LIST_ERROR,
						String.format("Judge resource (%s) is wrong!", className)
					);
				}
			}
		} catch (IOException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.JUDGE_ENTRY_LIST_ERROR,
				"Judge resource (judges.list) missing!"
			);
		}
	}

	private void appendToList(String switchInstruction, Class<? extends Judge> judge) throws AbstractBusinessException {
		if (switchInstruction.equalsIgnoreCase("ON")) {
			this.activeJudges.add(judge);
		} else if (switchInstruction.equalsIgnoreCase("OFF")) {
			this.inactiveJudges.add(judge);
		} else {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.JUDGE_ENTRY_LIST_ERROR,
				"Judge switch instruction should be either ON or OFF"
			);
		}
	}

	public List<Class<? extends Judge>> getActiveJudges() {
		return activeJudges;
	}

	public List<Class<? extends Judge>> getInactiveJudges() {
		return inactiveJudges;
	}
}