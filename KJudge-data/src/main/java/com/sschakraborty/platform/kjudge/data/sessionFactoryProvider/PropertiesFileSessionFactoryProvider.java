package com.sschakraborty.platform.kjudge.data.sessionFactoryProvider;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.StandardErrorCode;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileSessionFactoryProvider implements SessionFactoryProvider {
	private SessionFactory sessionFactory;

	@Override
	public void initialize(String configFileName) throws AbstractBusinessException {
		Properties properties = new Properties();
		FileReader fileReader = createFileReader(configFileName);
		try {
			properties.load(fileReader);
			Configuration configuration = this.buildConfiguration(properties);
			this.sessionFactory = configuration.buildSessionFactory();
		} catch (IOException e) {
			ExceptionUtility.throwGenericException(StandardErrorCode.GENERIC_ERROR, "IO exception occurred while reading properties file!");
		}
	}

	@Override
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	private Configuration buildConfiguration(Properties properties) {
		Configuration configuration = new Configuration();
		configuration.addProperties(properties);
		return configuration;
	}

	private FileReader createFileReader(String configFileName) throws AbstractBusinessException {
		try {
			return new FileReader(configFileName);
		} catch (FileNotFoundException e) {
			ExceptionUtility.throwGenericException(StandardErrorCode.INITIALIZATION_ERROR, "Failed to read the properties file during initialization of Session Factory");
		} catch (Exception e) {
			ExceptionUtility.throwGenericException(StandardErrorCode.GENERIC_ERROR, e.getLocalizedMessage());
		}
		return null;
	}
}
