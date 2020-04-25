package com.sschakraborty.platform.kjudge.data.transactionManager;

import com.sschakraborty.platform.kjudge.data.unit.StatelessTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;

public interface StatelessTransactionJob {
	<T> T[] execute(StatelessTransactionUnit transactionUnit) throws AbstractBusinessException;
}
