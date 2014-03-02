package com.programmerdan.fieldscan.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Helper root class for tests to handle setting up entity objects and persistence context.
 */
public class BaseTest {

	private static EntityManagerFactory emf = null;
	private EntityManager em=null;
	private EntityTransaction tx;

	protected EntityManager em() {
		return BaseTest.em;
	}

	@Before
	public void setup() {
		em = BaseTest.emf.getManager();
		tx = em.getTransaction();
		tx.begin();
	}

	@After
	public void teardown() {
		tx.close();
		em.close()
	}

	@BeforeClass
	public static void() {
		if (emf != null)
			emf.close();

		emf = Persistence.getEntityManagerFactory("com.programmerdan.fieldscan.test");
	}
}
