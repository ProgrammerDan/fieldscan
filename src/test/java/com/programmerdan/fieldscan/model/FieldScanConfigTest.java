package com.programmerdan.fieldscan.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

import javax.persistence.PersistenceException;
/**
 * Tests persistence and retrieval of a FieldScanConfigTest.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Initial release.
 */
public class FieldScanConfigTest extends BaseTest {

	/**
	 * Basic FieldScanConfig Test
	 */
	@Test
	public void fieldScanConfigPersistenceTest() {
		FieldScanConfig fsc = new FieldScanConfig(null,"fsc1","Base FSC Test", false, null);
		NodeProcessorConfig npc = new NodeProcessorConfig(null, "npc1", "FileNodeProcessor","a=b",fsc);  
		fsc.setRootProcessor(npc);
		em().persist(npc);
		em().persist(fsc);
		em().flush();

		FieldScanConfig fscP = em().find(FieldScanConfig.class, fsc.getId());

		assertEquals("Name mangled!", fsc.getConfigName(), fscP.getConfigName());
		assertEquals("Description mangled!", fsc.getDescription(), fscP.getDescription());
		assertFalse("Parallel Config mangled!", fscP.getIsParallelDeduplication());
		assertNotNull("Node Processor Config missing!", fscP.getRootProcessor());
		assertEquals("Node Processor Config is wrong", npc, fscP.getRootProcessor());
	}

	/**
	 * Negative test to ensure uniqueness constraint
	 */
	@Test(expected=PersistenceException.class)
	public void fieldScanConfigNameUniquenessTest() {
		FieldScanConfig fsc1 = new FieldScanConfig(null, "fsc2", "Base FSC Test 2", false, null);
		FieldScanConfig fsc2 = new FieldScanConfig(null, "fsc2", "Base FSC Test 3", false, null);
		NodeProcessorConfig npc1 = new NodeProcessorConfig(null, "npc2", "FileNodeProcessor","a=b",fsc1);  
		NodeProcessorConfig npc2 = new NodeProcessorConfig(null, "npc3", "FileNodeProcessor","a=b",fsc2);  
		fsc1.setRootProcessor(npc1);
		fsc2.setRootProcessor(npc2);
		em().persist(npc1);
		em().persist(npc2);
		em().persist(fsc1);
		em().persist(fsc2);
		em().flush();
	}

	/**
	 * Negative test to ensure nullable=false constraint is followed for parallel param
	 */
	@Test(expected=PersistenceException.class)
	public void fieldScanConfigParallelConfigMissingTest() {
		FieldScanConfig fsc1 = new FieldScanConfig(null, "fsc3", "Base FSC Test 3", null, null);
		NodeProcessorConfig npc1 = new NodeProcessorConfig(null, "npc4", "FileNodeProcessor","a=b",fsc1);  
		fsc1.setRootProcessor(npc1);
		em().persist(npc1);
		em().persist(fsc1);
		em().flush();
	}

	/**
	 * Negative test to ensure nullable=false constraint is followed for name
	 */
	@Test(expected=PersistenceException.class)
	public void fieldScanConfigNameMissingTest() {
		FieldScanConfig fsc1 = new FieldScanConfig(null, null, "Base FSC Test 4", true, null);
		NodeProcessorConfig npc1 = new NodeProcessorConfig(null, "npc4", "FileNodeProcessor","a=b",fsc1);  
		fsc1.setRootProcessor(npc1);
		em().persist(npc1);
		em().persist(fsc1);
		em().flush();
	}

	/**
	 * Negative test to ensure nullable=false constraint is followed for RootProcessor
	 */
	@Test(expected=PersistenceException.class)
	public void fieldScanConfigRootProcessorMissingTest() {
		FieldScanConfig fsc1 = new FieldScanConfig(null, "fsc4", "Base FSC Test 5", true, null);
		em().persist(fsc1);
		em().flush();
	}
}
