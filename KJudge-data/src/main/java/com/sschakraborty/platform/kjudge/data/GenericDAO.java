package com.sschakraborty.platform.kjudge.data;

import com.sschakraborty.platform.kjudge.data.dataProvider.DataProvider;
import com.sschakraborty.platform.kjudge.data.dataProvider.DefaultDataProvider;
import com.sschakraborty.platform.kjudge.data.transactionManager.StatefulTransactionJob;
import com.sschakraborty.platform.kjudge.data.transactionManager.StatelessTransactionJob;
import com.sschakraborty.platform.kjudge.data.transactionManager.TransactionManager;
import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.data.unit.StatelessTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GenericDAO {
	private final DataProvider dataProvider;
	private final TransactionManager transactionManager;

	public GenericDAO() throws AbstractBusinessException {
		this.dataProvider = new DefaultDataProvider();
		this.transactionManager = new TransactionManager(this.dataProvider);
	}

	public <T> T[] save(T... objectList) throws AbstractBusinessException {
		return this.transactionManager.executeStatefulJob(new StatefulTransactionJob() {
			@Override
			public <T> T[] execute(StatefulTransactionUnit transactionUnit) {
				for (Object object : objectList) {
					transactionUnit.getSession().save(object);
				}
				return (T[]) objectList;
			}
		});
	}

	public <T> T[] update(T... objectList) throws AbstractBusinessException {
		return this.transactionManager.executeStatefulJob(new StatefulTransactionJob() {
			@Override
			public <T> T[] execute(StatefulTransactionUnit transactionUnit) {
				for (Object object : objectList) {
					transactionUnit.getSession().update(object);
				}
				return (T[]) objectList;
			}
		});
	}

	public <T> T[] saveOrUpdate(T... objectList) throws AbstractBusinessException {
		return this.transactionManager.executeStatefulJob(new StatefulTransactionJob() {
			@Override
			public <T> T[] execute(StatefulTransactionUnit transactionUnit) {
				for (Object object : objectList) {
					transactionUnit.getSession().saveOrUpdate(object);
				}
				return (T[]) objectList;
			}
		});
	}

	public <T> T[] delete(T... objectList) throws AbstractBusinessException {
		return this.transactionManager.executeStatelessJob(new StatelessTransactionJob() {
			@Override
			public <T> T[] execute(StatelessTransactionUnit transactionUnit) {
				for (Object object : objectList) {
					transactionUnit.getSession().delete(object);
				}
				return (T[]) objectList;
			}
		});
	}

	public <T> List<T> fetch(Class<T> typeClass, Serializable... idList) throws AbstractBusinessException {
		final Object[] objects = this.transactionManager.executeStatelessJob(new StatelessTransactionJob() {
			@Override
			public Object[] execute(StatelessTransactionUnit transactionUnit) {
				Object[] objectArray = new Object[idList.length];
				int counter = 0;
				for (Serializable id : idList) {
					objectArray[counter++] = transactionUnit.getSession().get(typeClass, id);
				}
				return objectArray;
			}
		});
		final List<T> list = new ArrayList<>(objects.length);
		for (Object object : objects) {
			list.add((T) object);
		}
		return list;
	}

	public <T> T fetchFull(Class<T> typeClass, Serializable id) throws AbstractBusinessException {
		final Object[] objects = this.transactionManager.executeStatefulJob(new StatefulTransactionJob() {
			@Override
			public Object[] execute(StatefulTransactionUnit transactionUnit) {
				final EntityManagerFactory entityManagerFactory = transactionUnit.getSession().getEntityManagerFactory();
				final EntityManager entityManager = entityManagerFactory.createEntityManager();
				final Object[] objects = new Object[1];
				objects[0] = entityManager.find(typeClass, id);
				return objects;
			}
		});
		return (T) objects[0];
	}
}