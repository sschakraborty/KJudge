package com.sschakraborty.platform.kjudge.data.sessionFactoryProvider;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.GenericException;
import com.sschakraborty.platform.kjudge.error.errorCode.StandardErrorCode;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class PropertiesFileSessionFactoryProvider implements SessionFactoryProvider {
	private SessionFactory sessionFactory;

	@Override
	public void initialize(String configFileName, List<Class> classList) throws AbstractBusinessException {
		Properties properties = new Properties();
		InputStream inputStream = resourceInputStream(configFileName);
		try {
			properties.load(inputStream);
			this.closeStream(inputStream);
			Configuration configuration = this.buildConfiguration(properties, classList);
			this.sessionFactory = configuration.buildSessionFactory();
		} catch (IOException e) {
			ExceptionUtility.throwGenericException(StandardErrorCode.GENERIC_ERROR, "IO exception occurred while reading properties file!");
		}
	}

	private void closeStream(InputStream inputStream) throws GenericException {
		try {
			inputStream.close();
		} catch (IOException e) {
			ExceptionUtility.throwGenericException(StandardErrorCode.GENERIC_ERROR,
				String.format(
					"Failed to close resource stream: %s",
					e.getMessage()
				)
			);
		}
	}

	@Override
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	private Configuration buildConfiguration(Properties properties, List<Class> classList) {
		Configuration configuration = new Configuration();
		configuration.addProperties(properties);
		for (Class clazz : classList) {
			configuration.addAnnotatedClass(clazz);
		}
		return configuration;
	}

	private InputStream resourceInputStream(String configFileName) throws AbstractBusinessException {
		try {
			return getClass().getClassLoader().getResourceAsStream(configFileName);
		} catch (Exception e) {
			ExceptionUtility.throwGenericException(StandardErrorCode.INITIALIZATION_ERROR,
				String.format(
					"Failed to read the properties file during initialization of Session Factory: %s",
					e.getMessage()
				)
			);
		}
		return null;
	}
}
