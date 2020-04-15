package com.sschakraborty.platform.kjudge.data.dataProvider;

import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import org.hibernate.Transaction;

public interface DataProvider {
	Transaction statefulTransaction() throws AbstractBusinessException;

	Transaction statelessTransaction() throws AbstractBusinessException;
}
