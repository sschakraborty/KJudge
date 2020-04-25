package com.sschakraborty.platform.kjudge.data.unit;

import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

public class StatelessTransactionUnit extends AbstractTransactionUnit {
	private final StatelessSession session;

	public StatelessTransactionUnit(StatelessSession session, Transaction transaction) {
		super(transaction);
		this.session = session;
	}

	public StatelessSession getSession() {
		return session;
	}
}