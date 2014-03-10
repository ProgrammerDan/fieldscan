package com.programmerdan.fieldscan.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.PersistenceException;

/**
 * Tests persistence and retrieval of a DirNode.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Initial release.
 */
@RunWith(JUnit4.class)
public class DirNodeTest extends BaseTest {

	/**
	 * Basic DirNode test, validating that all relationships are handled correctly and persistence is functioning.
	 * TODO: this one test has many tests. Split them out.
	 */
	@Test
	public void dirNodePersistenceTest() {
		DirNode dn1 = new DirNode(1L,"root1", null, null, null);
		DirNode dn2 = new DirNode(2L,"child1", null, dn1, null);
		DirNode dn3 = new DirNode(3L,"childchild1", null, dn2, null);
		FileNode fn = new FileNode(1L, "file111.fle", new Byte[]{1}, new Byte[]{2}, 100L, false, dn3);
		em().persist(dn1);
		em().persist(dn2);
		em().persist(dn3);
		em().persist(fn);
		
		em().flush();

		DirNode dn1P = em().find(DirNode.class, dn1.getId());
		DirNode dn2P = em().find(DirNode.class, dn2.getId());
		DirNode dn3P = em().find(DirNode.class, dn3.getId());
		FileNode fnP = em().find(FileNode.class, fn.getId());

		assertEquals("Root dir directoryname mangled", dn1.getDirectoryName(), dn1P.getDirectoryName());
		assertNotNull("File list null!", dn1P.getFileNodes());
		assertEquals("Unexpected files in FileList", 0, dn1P.getFileNodes().size());
		assertNull("Unexpected parent of root node.", dn1P.getParentDir());
		assertNotNull("ChildDir list null!", dn1P.getChildDirs());
		assertEquals("ChildDirs is too large!",1, dn1P.getChildDirs().size());
		assertTrue("ChildDir missing!",dn1P.getChildDirs().contains(dn2P));
		assertEquals("Wrong parent dir!", dn1P, dn2P.getParentDir());
		assertNotNull("CC File list null!", dn3P.getFileNodes());
		assertEquals("Wrong number of files in CC FileList", 1, dn3P.getFileNodes().size());
		assertTrue("FileNode missing!", dn3P.getFileNodes().contains(fnP));
	}

	/**
	 * Negative test for failure on no directoryname.
	 * TODO: be more specific on exception.
	 */
	@Test(expected=PersistenceException.class)
	public void dirNodeFailsOnPersistWithoutDirectoryName() {
		DirNode dn = new DirNode();
		em().persist(dn);
		dn.setDirectoryName(null);
		dn.setParentDir(null); //allowed
		em().flush();
	}
}
