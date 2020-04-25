package com.sschakraborty.platform.kjudge.data.transactionManager;

import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;

public interface StatefulTransactionJob {
	<T> T[] execute(StatefulTransactionUnit transactionUnit) throws AbstractBusinessException;
}
