package com.sschakraborty.platform.kjudge.data.transactionProvider;

import com.sschakraborty.platform.kjudge.data.unit.AbstractTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;

public interface TransactionProvider {
	AbstractTransactionUnit newTransaction() throws AbstractBusinessException;

	AbstractTransactionUnit getCurrentTransaction() throws AbstractBusinessException;
}