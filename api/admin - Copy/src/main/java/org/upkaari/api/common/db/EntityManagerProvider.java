package org.upkaari.api.common.db;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProvider {

	@PersistenceContext
	private EntityManager em;
	public static EntityManager getEntityManager() {

		return null;
	}
	
	public static void closeEntityManager() {

	}
	
	public static EntityManagerFactory getEntityManagerFactory() {
		return null;
	}
}
