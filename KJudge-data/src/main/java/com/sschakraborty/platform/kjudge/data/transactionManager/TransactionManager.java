package com.sschakraborty.platform.kjudge.data.transactionManager;

import com.sschakraborty.platform.kjudge.data.dataProvider.DataProvider;
import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.data.unit.StatelessTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import com.sschakraborty.platform.kjudge.error.ExceptionUtility;
import com.sschakraborty.platform.kjudge.error.errorCode.StandardErrorCode;

public class TransactionManager {
	private final DataProvider dataProvider;

	public TransactionManager(DataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public <T> T[] executeStatefulJob(final StatefulTransactionJob job) throws AbstractBusinessException {
		final StatefulTransactionUnit transactionUnit = (StatefulTransactionUnit) this.dataProvider.statefulTransaction();
		T[] result = null;
		try {
			result = job.execute(transactionUnit);
			transactionUnit.getTransaction().commit();
		} catch (Exception e) {
			transactionUnit.getTransaction().rollback();
			handleException(e);
		} finally {
			transactionUnit.getSession().close();
		}
		return result;
	}

	public <T> T[] executeStatelessJob(final StatelessTransactionJob job) throws AbstractBusinessException {
		final StatelessTransactionUnit transactionUnit = (StatelessTransactionUnit) this.dataProvider.statelessTransaction();
		T[] result = null;
		try {
			result = job.execute(transactionUnit);
			transactionUnit.getTransaction().commit();
		} catch (Exception e) {
			transactionUnit.getTransaction().rollback();
			handleException(e);
		} finally {
			transactionUnit.getSession().close();
		}
		return result;
	}

	private void handleException(Exception e) throws AbstractBusinessException {
		if (e instanceof AbstractBusinessException) {
			throw (AbstractBusinessException) e;
		} else {
			ExceptionUtility.throwGenericException(
				StandardErrorCode.GENERIC_ERROR,
				String.format(
					"Error encountered while running transaction query: %s",
					e.getMessage()
				)
			);
		}
	}
}