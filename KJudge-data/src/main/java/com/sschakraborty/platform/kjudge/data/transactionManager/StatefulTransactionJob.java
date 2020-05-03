package com.sschakraborty.platform.kjudge.data.transactionManager;

import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;

import java.util.List;

public interface StatefulTransactionJob {
	<T> List<T> execute(StatefulTransactionUnit transactionUnit) throws AbstractBusinessException;
}
