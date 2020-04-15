package com.sschakraborty.platform.kjudge.data.sessionFactoryProvider;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import org.hibernate.SessionFactory;

public interface SessionFactoryProvider {
	void initialize(String configFileName) throws AbstractBusinessException;

	SessionFactory getSessionFactory() throws AbstractBusinessException;
}