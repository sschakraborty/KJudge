package com.sschakraborty.platform.kjudge.data.transactionProvider;

import com.sschakraborty.platform.kjudge.data.sessionProvider.SessionProvider;
import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.StandardErrorCode;
import org.hibernate.Session;

public class StatefulSessionTransactionProvider implements TransactionProvider {
	private final SessionProvider sessionProvider;

	public StatefulSessionTransactionProvider(SessionProvider sessionProvider) throws AbstractBusinessException {
		this.sessionProvider = sessionProvider;
		if (this.sessionProvider == null) {
			ExceptionUtility.throwGenericException(StandardErrorCode.OBJECT_CANNOT_BE_NULL, "Session provider cannot be null!");
		}
	}

	private Session fetchSession() throws AbstractBusinessException {
		Session session = this.sessionProvider.openSession();
		if (session == null) {
			ExceptionUtility.throwGenericException(StandardErrorCode.OBJECT_CANNOT_BE_NULL, "Session provider provided a null session!");
		}
		return session;
	}

	private Session currentSession() throws AbstractBusinessException {
		Session session = this.sessionProvider.getCurrentSession();
		if (session == null) {
			ExceptionUtility.throwGenericException(StandardErrorCode.OBJECT_CANNOT_BE_NULL, "Session provider provided a null session!");
		}
		return session;
	}

	@Override
	public StatefulTransactionUnit newTransaction() throws AbstractBusinessException {
		Session session = this.fetchSession();
		return new StatefulTransactionUnit(session, session.beginTransaction());
	}

	@Override
	public StatefulTransactionUnit getCurrentTransaction() throws AbstractBusinessException {
		Session session = this.currentSession();
		return new StatefulTransactionUnit(session, session.getTransaction());
	}
}