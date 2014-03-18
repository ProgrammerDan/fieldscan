package com.programmerdan.fieldscan.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;

import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;


/**
 * Helper root class for tests to handle setting up entity objects and persistence context.
 * 
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Initial version of base class.
 */
public class BaseTest {

	static EntityManagerFactory emf = null;
	private EntityManager em=null;
	private EntityTransaction tx=null;

	protected EntityManager em() {
		return em;
	}

	@Before
	public void setup() {
		em = BaseTest.emf.createEntityManager();
		tx = em.getTransaction();
		tx.begin();
	}

	@After
	public void teardown() {
		if (tx != null) {
			if (tx.getRollbackOnly()) {
				tx.rollback();
			} else {
				tx.commit();
			}
		}
		if (em != null) {
			em.close();
		}
	}

	@BeforeClass
	public static void setupClass() {
		if (emf == null)
			emf = Persistence.createEntityManagerFactory("com.programmerdan.fieldscan.test");
	}
}
