package com.programmerdan.fieldscan.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests persistence and retreival of a FileNode.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Initial release.
 */
public class FileNodeTest extends BaseTest {

	@Test
	public void fileNodePersistenceTest() {
		DirNode dn = new DirNode(1L,"root1", null, null);
		FileNode fn = new FileNode(1L, "file1", new Byte[]{1}, new Byte[]{2}, 100L, false, dn);
		em().persist(dn);
		em().persist(fn);

		em().flush();

		FileNode fnP = em().find(FileNode.class, fn.getId());

		assertEquals("Name mangled", fn.getFileName(), fnP.getFileName());
		assertEquals("OneKbHash mangled",fn.getOneKbHash(),fnP.getOneKbHash());
		assertEquals("FullHash mangled",fn.getFullHash(),fnP.getFullHash());
		assertEquals("Size mangled",fn.getFileSize(),fnP.getFileSize());
		assertEquals("Gone Flag mangled",fn.getIsGone(),fnP.getIsGone());
		assertNotNull("DirNode is null!",fnP.getDirNode());
		assertEquals("DirNode is altered",dn.getDirectoryName(), fnP.getDirNode().getDirectoryName());
	}

	// TODO: negative tests for null constraints
	
	/**
	 * Testing for expected error state of no DirNode. TODO: narrow exception expectation.
	 */
	@Test(expects=PersistenceException)
	public void fileNodeFailsOnPersistWithoutDirNode {
		FileNode file2 = new FileNode()
		em().persist(file2);
		file2.setFileName("file2");
		file2.setOneKbHash(new Byte[]{2});
		file2.setFullHash(new Byte[]{3});
		file2.setFileSize(101L);
		file2.setIsGone(true);
		file2.setDirNode(null);

		em.flush();
	}

	/**
	 * Testing for expected error state of no FileName. TODO: narrow exception expectation.
	 */
	@Test(expects=PersistenceException)
	public void fileNodeFailsOnPersistWithoutFileName {
		DirNode dn = new DirNode(1L,"root3", null, null);
		em().persist(dn);

		FileNode file2 = new FileNode()
		em().persist(file2);
		file2.setFileName(null);
		file2.setOneKbHash(new Byte[]{2});
		file2.setFullHash(new Byte[]{3});
		file2.setFileSize(101L);
		file2.setIsGone(true);
		file2.setDirNode(dn);

		em.flush();
	}

	/**
	 * Testing for expected error state of no OneKbHash. TODO: narrow exception expectation.
	 */
	@Test(expects=PersistenceException)
	public void fileNodeFailsOnPersistWithoutOneKbHash {
		DirNode dn = new DirNode(1L,"root4", null, null);
		em().persist(dn);

		FileNode file2 = new FileNode()
		em().persist(file2);
		file2.setFileName("file3");
		file2.setOneKbHash(null);
		file2.setFullHash(new Byte[]{3});
		file2.setFileSize(101L);
		file2.setIsGone(true);
		file2.setDirNode(dn);

		em.flush();
	}

	/**
	 * Testing for expected error state of no FullHash. TODO: narrow exception expectation.
	 */
	@Test(expects=PersistenceException)
	public void fileNodeFailsOnPersistWithoutFullHash {
		DirNode dn = new DirNode(1L,"root5", null, null);
		em().persist(dn);

		FileNode file2 = new FileNode()
		em().persist(file2);
		file2.setFileName("file4");
		file2.setOneKbHash(new Byte[]{4});
		file2.setFullHash(null);
		file2.setFileSize(101L);
		file2.setIsGone(true);
		file2.setDirNode(dn);

		em.flush();
	}

}
