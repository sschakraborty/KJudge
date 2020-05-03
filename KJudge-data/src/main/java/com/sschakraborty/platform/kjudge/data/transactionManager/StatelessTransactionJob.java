package com.sschakraborty.platform.kjudge.data.transactionManager;

import com.sschakraborty.platform.kjudge.data.unit.StatelessTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;

import java.util.List;

public interface StatelessTransactionJob {
	<T> List<T> execute(StatelessTransactionUnit transactionUnit) throws AbstractBusinessException;
}
