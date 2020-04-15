package com.sschakraborty.platform.kjudge.data.sessionProvider;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import org.hibernate.Session;
import org.hibernate.StatelessSession;

public interface SessionProvider {
	Session openSession() throws AbstractBusinessException;

	StatelessSession openStatelessSession() throws AbstractBusinessException;

	Session getCurrentSession() throws AbstractBusinessException;
}
