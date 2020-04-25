package com.sschakraborty.platform.kjudge.data.transactionProvider;

import com.sschakraborty.platform.kjudge.data.sessionProvider.SessionProvider;
import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.StandardErrorCode;
import org.hibernate.Session;

public class StatefulSessionTransactionProvider implements TransactionProvider {
	private final SessionProvider sessionProvider;
	private Session session;

	public StatefulSessionTransactionProvider(SessionProvider sessionProvider) throws AbstractBusinessException {
		this.sessionProvider = sessionProvider;
		if (this.sessionProvider == null) {
			ExceptionUtility.throwGenericException(StandardErrorCode.OBJECT_CANNOT_BE_NULL, "Session provider cannot be null!");
		}
	}

	private void fetchSession() throws AbstractBusinessException {
		this.session = this.sessionProvider.openSession();
		if (this.session == null) {
			ExceptionUtility.throwGenericException(StandardErrorCode.OBJECT_CANNOT_BE_NULL, "Session provider provided a null session!");
		}
	}

	private void currentSession() throws AbstractBusinessException {
		this.session = this.sessionProvider.getCurrentSession();
		if (this.session == null) {
			ExceptionUtility.throwGenericException(StandardErrorCode.OBJECT_CANNOT_BE_NULL, "Session provider provided a null session!");
		}
	}

	@Override
	public StatefulTransactionUnit newTransaction() throws AbstractBusinessException {
		this.fetchSession();
		return new StatefulTransactionUnit(this.session, this.session.beginTransaction());
	}

	@Override
	public StatefulTransactionUnit getCurrentTransaction() throws AbstractBusinessException {
		this.currentSession();
		return new StatefulTransactionUnit(this.session, this.session.getTransaction());
	}
}