package com.sschakraborty.platform.kjudge.data.sessionFactoryProvider;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import org.hibernate.SessionFactory;

import java.util.List;

public interface SessionFactoryProvider {
	void initialize(String configFileName, List<Class> classList) throws AbstractBusinessException;

	SessionFactory getSessionFactory() throws AbstractBusinessException;
}