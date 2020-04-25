package com.sschakraborty.platform.kjudge.data.unit;

import org.hibernate.Transaction;

public abstract class AbstractTransactionUnit {
	private final Transaction transaction;

	public AbstractTransactionUnit(Transaction transaction) {
		this.transaction = transaction;
	}

	public Transaction getTransaction() {
		return transaction;
	}
}
