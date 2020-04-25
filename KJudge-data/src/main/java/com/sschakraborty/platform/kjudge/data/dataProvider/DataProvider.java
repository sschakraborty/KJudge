package com.sschakraborty.platform.kjudge.data.dataProvider;

import com.sschakraborty.platform.kjudge.data.unit.AbstractTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;

public interface DataProvider {
	AbstractTransactionUnit statefulTransaction() throws AbstractBusinessException;

	AbstractTransactionUnit statelessTransaction() throws AbstractBusinessException;
}
