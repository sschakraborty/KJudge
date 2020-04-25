package com.sschakraborty.platform.kjudge.data.transactionProvider;

import com.sschakraborty.platform.kjudge.data.sessionProvider.SessionProvider;
import com.sschakraborty.platform.kjudge.data.unit.StatelessTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.StandardErrorCode;
import org.hibernate.StatelessSession;

public class StatelessSessionTransactionProvider implements TransactionProvider {
	private final SessionProvider sessionProvider;

	public StatelessSessionTransactionProvider(SessionProvider sessionProvider) throws AbstractBusinessException {
		this.sessionProvider = sessionProvider;
		if (this.sessionProvider == null) {
			ExceptionUtility.throwGenericException(StandardErrorCode.OBJECT_CANNOT_BE_NULL, "Session provider cannot be null!");
		}
	}

	private StatelessSession fetchSession() throws AbstractBusinessException {
		StatelessSession statelessSession = this.sessionProvider.openStatelessSession();
		if (statelessSession == null) {
			ExceptionUtility.throwGenericException(StandardErrorCode.OBJECT_CANNOT_BE_NULL, "Session provider provided a null session!");
		}
		return statelessSession;
	}

	@Override
	public StatelessTransactionUnit newTransaction() throws AbstractBusinessException {
		StatelessSession statelessSession = this.fetchSession();
		return new StatelessTransactionUnit(statelessSession, statelessSession.beginTransaction());
	}

	@Override
	public StatelessTransactionUnit getCurrentTransaction() throws AbstractBusinessException {
		StatelessSession statelessSession = this.fetchSession();
		return new StatelessTransactionUnit(statelessSession, statelessSession.getTransaction());
	}
}