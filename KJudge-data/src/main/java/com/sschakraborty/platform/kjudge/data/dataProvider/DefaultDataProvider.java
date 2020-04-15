package com.sschakraborty.platform.kjudge.data.dataProvider;

import com.sschakraborty.platform.kjudge.data.sessionFactoryProvider.SessionFactoryProvider;
import com.sschakraborty.platform.kjudge.data.sessionFactoryProvider.XMLConfigurationSessionFactoryProvider;
import com.sschakraborty.platform.kjudge.data.sessionProvider.DefaultSessionProvider;
import com.sschakraborty.platform.kjudge.data.sessionProvider.SessionProvider;
import com.sschakraborty.platform.kjudge.data.transactionProvider.StatefulSessionTransactionProvider;
import com.sschakraborty.platform.kjudge.data.transactionProvider.StatelessSessionTransactionProvider;
import com.sschakraborty.platform.kjudge.data.transactionProvider.TransactionProvider;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import org.hibernate.Transaction;

public class DefaultDataProvider implements DataProvider {
	private static final String HIBERNATE_CONFIG_FILE_NAME = "hibernate.cfg.xml";

	private final SessionFactoryProvider sessionFactoryProvider;
	private final SessionProvider sessionProvider;
	private final TransactionProvider statefulTransactionProvider;
	private final TransactionProvider statelessTransactionProvider;

	public DefaultDataProvider() throws AbstractBusinessException {
		this.sessionFactoryProvider = buildSessionFactoryProvider();
		this.sessionProvider = buildSessionProvider();
		this.statefulTransactionProvider = buildStatefulTransactionProvider();
		this.statelessTransactionProvider = buildStatelessTransactionProvider();
	}

	private TransactionProvider buildStatelessTransactionProvider() throws AbstractBusinessException {
		return new StatelessSessionTransactionProvider(this.sessionProvider);
	}

	private TransactionProvider buildStatefulTransactionProvider() throws AbstractBusinessException {
		return new StatefulSessionTransactionProvider(this.sessionProvider);
	}

	private SessionProvider buildSessionProvider() throws AbstractBusinessException {
		return new DefaultSessionProvider(this.sessionFactoryProvider);
	}

	private SessionFactoryProvider buildSessionFactoryProvider() throws AbstractBusinessException {
		SessionFactoryProvider provider = new XMLConfigurationSessionFactoryProvider();
		provider.initialize(HIBERNATE_CONFIG_FILE_NAME);
		return provider;
	}

	@Override
	public Transaction statefulTransaction() throws AbstractBusinessException {
		return this.statefulTransactionProvider.newTransaction();
	}

	@Override
	public Transaction statelessTransaction() throws AbstractBusinessException {
		return this.statelessTransactionProvider.newTransaction();
	}
}