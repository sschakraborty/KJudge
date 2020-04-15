package com.sschakraborty.platform.kjudge.data.transactionProvider;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import org.hibernate.Transaction;

public interface TransactionProvider {
	Transaction newTransaction() throws AbstractBusinessException;

	Transaction getCurrentTransaction() throws AbstractBusinessException;
}