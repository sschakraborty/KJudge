package com.sschakraborty.platform.kjudge.data.sessionProvider;

import com.sschakraborty.platform.kjudge.data.sessionFactoryProvider.SessionFactoryProvider;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.StandardErrorCode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;

public class DefaultSessionProvider implements SessionProvider {
	private final SessionFactoryProvider sessionFactoryProvider;

	public DefaultSessionProvider(SessionFactoryProvider sessionFactoryProvider) throws AbstractBusinessException {
		this.sessionFactoryProvider = sessionFactoryProvider;
		if (this.sessionFactoryProvider == null) {
			ExceptionUtility.throwGenericException(StandardErrorCode.OBJECT_CANNOT_BE_NULL, "Session factory provider cannot be null!");
		}
	}

	private SessionFactory fetchSessionFactory() throws AbstractBusinessException {
		final SessionFactory sessionFactory = this.sessionFactoryProvider.getSessionFactory();
		if (sessionFactory == null) {
			ExceptionUtility.throwGenericException(StandardErrorCode.OBJECT_CANNOT_BE_NULL, "Session factory provider provided a null session factory!");
		}
		return sessionFactory;
	}

	@Override
	public Session openSession() throws AbstractBusinessException {
		return fetchSessionFactory().openSession();
	}

	@Override
	public StatelessSession openStatelessSession() throws AbstractBusinessException {
		return fetchSessionFactory().openStatelessSession();
	}

	@Override
	public Session getCurrentSession() throws AbstractBusinessException {
		return fetchSessionFactory().getCurrentSession();
	}
}
