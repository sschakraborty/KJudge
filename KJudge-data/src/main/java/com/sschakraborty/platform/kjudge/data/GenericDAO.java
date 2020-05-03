package com.sschakraborty.platform.kjudge.data;

import com.sschakraborty.platform.kjudge.data.dataProvider.DataProvider;
import com.sschakraborty.platform.kjudge.data.dataProvider.DefaultDataProvider;
import com.sschakraborty.platform.kjudge.data.transactionManager.StatefulTransactionJob;
import com.sschakraborty.platform.kjudge.data.transactionManager.StatelessTransactionJob;
import com.sschakraborty.platform.kjudge.data.transactionManager.TransactionManager;
import com.sschakraborty.platform.kjudge.data.unit.StatefulTransactionUnit;
import com.sschakraborty.platform.kjudge.data.unit.StatelessTransactionUnit;
import com.sschakraborty.platform.kjudge.error.AbstractBusinessException;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericDAO {
	private final DataProvider dataProvider;
	private final TransactionManager transactionManager;

	public GenericDAO() throws AbstractBusinessException {
		this.dataProvider = new DefaultDataProvider();
		this.transactionManager = new TransactionManager(this.dataProvider);
	}

	public <T> List<T> save(T... objectList) throws AbstractBusinessException {
		return this.transactionManager.executeStatefulJob(new StatefulTransactionJob() {
			@Override
			public <T> List<T> execute(StatefulTransactionUnit transactionUnit) {
				for (Object object : objectList) {
					transactionUnit.getSession().save(object);
				}
				return Arrays.asList((T[]) objectList);
			}
		});
	}

	public <T> List<T> update(T... objectList) throws AbstractBusinessException {
		return this.transactionManager.executeStatefulJob(new StatefulTransactionJob() {
			@Override
			public <T> List<T> execute(StatefulTransactionUnit transactionUnit) {
				for (Object object : objectList) {
					transactionUnit.getSession().update(object);
				}
				return Arrays.asList((T[]) objectList);
			}
		});
	}

	public <T> List<T> saveOrUpdate(T... objectList) throws AbstractBusinessException {
		return this.transactionManager.executeStatefulJob(new StatefulTransactionJob() {
			@Override
			public <T> List<T> execute(StatefulTransactionUnit transactionUnit) {
				for (Object object : objectList) {
					transactionUnit.getSession().saveOrUpdate(object);
				}
				return Arrays.asList((T[]) objectList);
			}
		});
	}

	public <T> List<T> delete(T... objectList) throws AbstractBusinessException {
		return this.transactionManager.executeStatelessJob(new StatelessTransactionJob() {
			@Override
			public <T> List<T> execute(StatelessTransactionUnit transactionUnit) {
				for (Object object : objectList) {
					transactionUnit.getSession().delete(object);
				}
				return Arrays.asList((T[]) objectList);
			}
		});
	}

	public <T> List<T> fetch(Class<T> typeClass, Serializable... idList) throws AbstractBusinessException {
		return this.transactionManager.executeStatelessJob(new StatelessTransactionJob() {
			@Override
			public List<T> execute(StatelessTransactionUnit transactionUnit) {
				List<T> list = new ArrayList<>();
				for (Serializable id : idList) {
					list.add((T) transactionUnit.getSession().get(typeClass, id));
				}
				return list;
			}
		});
	}

	public <T> T fetchFull(Class<T> typeClass, Serializable id) throws AbstractBusinessException {
		final List<T> list = this.transactionManager.executeStatefulJob(new StatefulTransactionJob() {
			@Override
			public List<T> execute(StatefulTransactionUnit transactionUnit) {
				final EntityManagerFactory entityManagerFactory = transactionUnit.getSession().getEntityManagerFactory();
				final EntityManager entityManager = entityManagerFactory.createEntityManager();
				return Arrays.asList(entityManager.find(typeClass, id));
			}
		});
		return list.get(0);
	}

	public <T> List<T> fetchAll(Class<T> typeClass) throws AbstractBusinessException {
		return this.transactionManager.executeStatefulJob(new StatefulTransactionJob() {
			@Override
			public <T> List<T> execute(StatefulTransactionUnit transactionUnit) {
				final Session session = transactionUnit.getSession();
				final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
				final CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(typeClass);
				final Root<T> rootEntry = (Root<T>) criteriaQuery.from(typeClass);
				final CriteriaQuery<T> all = criteriaQuery.select(rootEntry);
				final TypedQuery<T> allQuery = session.createQuery(all);
				return allQuery.getResultList();
			}
		});
	}
}