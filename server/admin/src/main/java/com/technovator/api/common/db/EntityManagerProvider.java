package com.technovator.api.common.db;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProvider {

	@PersistenceContext
	private EntityManager em;
	public static EntityManager getEntityManager() {
		return CDI.current().select(EntityManager.class).get();
	}
	
	public static void closeEntityManager() {

	}
	
	public static EntityManagerFactory getEntityManagerFactory() {
		return null;
	}
}
