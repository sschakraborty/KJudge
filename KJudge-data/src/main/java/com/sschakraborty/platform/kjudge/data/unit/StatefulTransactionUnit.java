package com.sschakraborty.platform.kjudge.data.unit;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class StatefulTransactionUnit extends AbstractTransactionUnit {
	private final Session session;

	public StatefulTransactionUnit(Session session, Transaction transaction) {
		super(transaction);
		this.session = session;
	}

	public Session getSession() {
		return session;
	}
}