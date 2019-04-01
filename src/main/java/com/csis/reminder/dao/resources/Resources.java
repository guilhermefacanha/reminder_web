package com.csis.reminder.dao.resources;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Class responsible for providing EntityManager objects for DAO layer
 */
public class Resources {

	private static EntityManagerFactory factory = null;

	static {
		try {
			factory = Persistence.createEntityManagerFactory("pu");
		} catch (Exception e) {
			System.out.println("ERROR INITIALIZING FACTORY JPA");
			e.printStackTrace();
		}
	}

	/**
	 * @return {@link EntityManager}  - connection manager with hibernate context
	 */
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	public static void testInitializeDb() {
		getEntityManager().createNativeQuery("SELECT 1").getSingleResult();
	}

}