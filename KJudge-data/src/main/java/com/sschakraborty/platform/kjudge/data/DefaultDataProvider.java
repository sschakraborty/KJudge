package com.sschakraborty.platform.kjudge.data;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class DefaultDataProvider implements DataProvider {
	private final SessionFactory sessionFactory;
	private final StandardServiceRegistry standardServiceRegistry;

	public DefaultDataProvider() {
		this.standardServiceRegistry = this.initServiceRegistry();
		this.sessionFactory = this.initSessionFactory();
	}

	private SessionFactory initSessionFactory() {
		try {
			MetadataSources metadataSources = new MetadataSources(this.standardServiceRegistry);
			return metadataSources.buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			StandardServiceRegistryBuilder.destroy(this.standardServiceRegistry);
		}
		return null;
	}

	private StandardServiceRegistry initServiceRegistry() {
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
		return builder.build();
	}
}
