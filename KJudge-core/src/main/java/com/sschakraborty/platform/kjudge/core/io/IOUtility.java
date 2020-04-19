package com.sschakraborty.platform.kjudge.core.io;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.GenericException;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;

import java.io.*;

public class IOUtility {
	public static BufferedReader wrapReader(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream));
	}

	public static void writeToFile(String baseDir, String fileName, String content) throws AbstractBusinessException {
		createBaseDirectory(baseDir);
		File file = createFileIfNotExists(baseDir + "/" + fileName);
		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			PrintWriter writer = new PrintWriter(fileOutputStream);
			writer.write(content);
			writer.close();
		} catch (FileNotFoundException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_COMMAND_EXEC,
				String.format(
					"File (%s) was not found!",
					file.getAbsolutePath()
				)
			);
		} catch (IOException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_COMMAND_EXEC,
				String.format(
					"File (%s) is not writable!",
					file.getAbsolutePath()
				)
			);
		}
	}

	private static File createFileIfNotExists(String filePath) throws AbstractBusinessException {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.IO_ERROR_IN_COMMAND_EXEC,
					String.format(
						"File (%s) could not be created!",
						file.getAbsolutePath()
					)
				);
			}
		}
		return file;
	}

	private static void createBaseDirectory(String baseDir) throws GenericException {
		File baseDirectory = new File(baseDir);

		if (baseDirectory.exists() && !baseDirectory.isDirectory()) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.IO_ERROR_IN_COMMAND_EXEC,
				String.format(
					"Base directory path (%s) refers to a file instead of a directory!",
					baseDir
				)
			);
		}

		if (!baseDirectory.exists()) {
			baseDirectory.mkdirs();
		}
	}
}