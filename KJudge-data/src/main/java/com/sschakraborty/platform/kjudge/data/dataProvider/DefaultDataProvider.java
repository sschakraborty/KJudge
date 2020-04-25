package com.sschakraborty.platform.kjudge.data.dataProvider;

import com.sschakraborty.platform.kjudge.data.ClassRegistry;
import com.sschakraborty.platform.kjudge.data.sessionFactoryProvider.PropertiesFileSessionFactoryProvider;
import com.sschakraborty.platform.kjudge.data.sessionFactoryProvider.SessionFactoryProvider;
import com.sschakraborty.platform.kjudge.data.sessionProvider.DefaultSessionProvider;
import com.sschakraborty.platform.kjudge.data.sessionProvider.SessionProvider;
import com.sschakraborty.platform.kjudge.data.transactionProvider.StatefulSessionTransactionProvider;
import com.sschakraborty.platform.kjudge.data.transactionProvider.StatelessSessionTransactionProvider;
import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.data.unit.StatelessTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;

import java.util.List;

public class DefaultDataProvider implements DataProvider {
	private static final String HIBERNATE_CONFIG_FILE_NAME = "hibernate.properties";

	private final SessionFactoryProvider sessionFactoryProvider;
	private final SessionProvider sessionProvider;
	private final StatefulSessionTransactionProvider statefulTransactionProvider;
	private final StatelessSessionTransactionProvider statelessTransactionProvider;

	public DefaultDataProvider() throws AbstractBusinessException {
		this.sessionFactoryProvider = buildSessionFactoryProvider(this.loadClasses());
		this.sessionProvider = buildSessionProvider();
		this.statefulTransactionProvider = buildStatefulTransactionProvider();
		this.statelessTransactionProvider = buildStatelessTransactionProvider();
	}

	private List<Class> loadClasses() {
		return ClassRegistry.getClassList();
	}

	private StatelessSessionTransactionProvider buildStatelessTransactionProvider() throws AbstractBusinessException {
		return new StatelessSessionTransactionProvider(this.sessionProvider);
	}

	private StatefulSessionTransactionProvider buildStatefulTransactionProvider() throws AbstractBusinessException {
		return new StatefulSessionTransactionProvider(this.sessionProvider);
	}

	private SessionProvider buildSessionProvider() throws AbstractBusinessException {
		return new DefaultSessionProvider(this.sessionFactoryProvider);
	}

	private SessionFactoryProvider buildSessionFactoryProvider(List<Class> classList) throws AbstractBusinessException {
		SessionFactoryProvider provider = new PropertiesFileSessionFactoryProvider();
		provider.initialize(HIBERNATE_CONFIG_FILE_NAME, classList);
		return provider;
	}

	@Override
	public StatefulTransactionUnit statefulTransaction() throws AbstractBusinessException {
		return this.statefulTransactionProvider.newTransaction();
	}

	@Override
	public StatelessTransactionUnit statelessTransaction() throws AbstractBusinessException {
		return this.statelessTransactionProvider.newTransaction();
	}
}