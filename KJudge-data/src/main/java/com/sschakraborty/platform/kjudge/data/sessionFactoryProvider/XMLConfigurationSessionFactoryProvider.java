package com.sschakraborty.platform.kjudge.data.sessionFactoryProvider;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class XMLConfigurationSessionFactoryProvider implements SessionFactoryProvider {
	private SessionFactory sessionFactory;
	private StandardServiceRegistry standardServiceRegistry;

	@Override
	public void initialize(String configFileName, List<Class> classList) {
		this.standardServiceRegistry = this.initServiceRegistry(configFileName);
		this.sessionFactory = this.initSessionFactory(classList);
	}

	@Override
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	private SessionFactory initSessionFactory(List<Class> classList) {
		try {
			MetadataSources metadataSources = new MetadataSources(this.standardServiceRegistry);
			for (Class clazz : classList) {
				metadataSources.addAnnotatedClass(clazz);
			}
			return metadataSources.buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			StandardServiceRegistryBuilder.destroy(this.standardServiceRegistry);
		}
		return null;
	}

	private StandardServiceRegistry initServiceRegistry(String configFileName) {
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
		return builder.configure(configFileName).build();
	}
}
