/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.storage.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author LLASZKIE
 *
 */
public class EntityManagerProvider {

	private static final EntityManagerFactory entityManagerFactory;
	
	static {
		try {
			entityManagerFactory = Persistence
					.createEntityManagerFactory("com.capgemini.nsc.arch.imdg.details.storage.jpa");

		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();

	}
}
