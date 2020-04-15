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
	private SessionFactory sessionFactory;

	public DefaultSessionProvider(SessionFactoryProvider sessionFactoryProvider) throws AbstractBusinessException {
		this.sessionFactoryProvider = sessionFactoryProvider;
		if (this.sessionFactoryProvider == null) {
			ExceptionUtility.throwGenericException(StandardErrorCode.OBJECT_CANNOT_BE_NULL, "Session factory provider cannot be null!");
		}
		this.fetchSessionFactory(sessionFactoryProvider);
	}

	private void fetchSessionFactory(SessionFactoryProvider sessionFactoryProvider) throws AbstractBusinessException {
		this.sessionFactory = sessionFactoryProvider.getSessionFactory();
		if (this.sessionFactory == null) {
			ExceptionUtility.throwGenericException(StandardErrorCode.OBJECT_CANNOT_BE_NULL, "Session factory provider provided a null session factory!");
		}
	}

	@Override
	public Session openSession() {
		return sessionFactory.openSession();
	}

	@Override
	public StatelessSession openStatelessSession() {
		return sessionFactory.openStatelessSession();
	}

	@Override
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
}
