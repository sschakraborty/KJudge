package com.sschakraborty.platform.kjudge.data.transactionProvider;

import com.sschakraborty.platform.kjudge.data.sessionProvider.SessionProvider;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.StandardErrorCode;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

public class StatelessSessionTransactionProvider implements TransactionProvider {
	private final SessionProvider sessionProvider;
	private StatelessSession session;

	public StatelessSessionTransactionProvider(SessionProvider sessionProvider) throws AbstractBusinessException {
		this.sessionProvider = sessionProvider;
		if (this.sessionProvider == null) {
			ExceptionUtility.throwGenericException(StandardErrorCode.OBJECT_CANNOT_BE_NULL, "Session provider cannot be null!");
		}
		this.fetchSession();
	}

	private void fetchSession() throws AbstractBusinessException {
		this.session = this.sessionProvider.openStatelessSession();
		if (this.session == null) {
			ExceptionUtility.throwGenericException(StandardErrorCode.OBJECT_CANNOT_BE_NULL, "Session provider provided a null session!");
		}
	}

	@Override
	public Transaction newTransaction() {
		return this.session.beginTransaction();
	}

	@Override
	public Transaction getCurrentTransaction() {
		return this.session.getTransaction();
	}
}