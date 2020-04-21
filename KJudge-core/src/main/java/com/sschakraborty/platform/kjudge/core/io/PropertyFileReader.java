package com.sschakraborty.platform.kjudge.core.io;

import com.sschakraborty.platform.kjudge.core.Judge;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.JudgeErrorCode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {
	public Properties readJudgeProperties(Class<? extends Judge> judgeClass) throws AbstractBusinessException {
		return this.readPropertiesUsingFileName(judgeClass.getSimpleName() + ".properties");
	}

	public Properties readPropertiesUsingFileName(String fileName) throws AbstractBusinessException {
		InputStream inputStream = getResourceFile(fileName);
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.JUDGE_PROPERTIES_ERROR,
				String.format("Judge properties file (%s) is corrupt!", fileName)
			);
		}
		return properties;
	}

	private InputStream getResourceFile(String fileName) throws AbstractBusinessException {
		InputStream inputStream = null;
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
			if (inputStream.available() <= 0) {
				ExceptionUtility.throwGenericException(
					JudgeErrorCode.JUDGE_PROPERTIES_ERROR,
					String.format("Judge properties file (%s) is empty!", fileName)
				);
			}
		} catch (IOException e) {
			ExceptionUtility.throwGenericException(
				JudgeErrorCode.JUDGE_PROPERTIES_ERROR,
				String.format("Judge properties file (%s) is missing!", fileName)
			);
		}
		return inputStream;
	}
}