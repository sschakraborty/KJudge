package com.sschakraborty.platform.kjudge.core;

import com.sschakraborty.platform.kjudge.core.io.PropertyFileReader;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;

import java.util.Properties;

public abstract class AbstractJudge implements Judge {
	private final Properties properties;
	private final PropertyFileReader propertyFileReader;

	public AbstractJudge() throws AbstractBusinessException {
		this.propertyFileReader = new PropertyFileReader();
		this.properties = this.readProperties();
		this.checkPropertiesPresent();
		this.checkEnvironmentReady();
	}

	protected abstract void checkPropertiesPresent() throws AbstractBusinessException;

	public Properties getProperties() {
		return properties;
	}

	public PropertyFileReader getPropertyFileReader() {
		return propertyFileReader;
	}

	protected abstract Properties readProperties() throws AbstractBusinessException;

	protected abstract void checkEnvironmentReady() throws AbstractBusinessException;
}