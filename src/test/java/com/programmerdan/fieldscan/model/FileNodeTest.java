package com.programmerdan.fieldscan.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.PersistenceException;
/**
 * Tests persistence and retrieval of a FileNode.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Initial release.
 */
public class FileNodeTest extends BaseTest {

	@Test
	public void fileNodePersistenceTest() {
		DirNode dn = new DirNode(null,"root1", null);
		FileNode fn = new FileNode(null, "file1", new Byte[]{1}, new Byte[]{2}, 100L, false, dn);
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
	@Test(expected=PersistenceException.class)
	public void fileNodeFailsOnPersistWithoutDirNodeTest() {
		FileNode file2 = new FileNode();
		em().persist(file2);
		file2.setFileName("file2");
		file2.setOneKbHash(new Byte[]{2});
		file2.setFullHash(new Byte[]{3});
		file2.setFileSize(101L);
		file2.setIsGone(true);
		file2.setDirNode(null);

		em().flush();
	}

	/**
	 * Testing for expected error state of no FileName. TODO: narrow exception expectation.
	 */
	@Test(expected=PersistenceException.class)
	public void fileNodeFailsOnPersistWithoutFileNameTest() {
		DirNode dn = new DirNode(null,"root3", null);
		em().persist(dn);

		FileNode file2 = new FileNode();
		em().persist(file2);
		file2.setFileName(null);
		file2.setOneKbHash(new Byte[]{2});
		file2.setFullHash(new Byte[]{3});
		file2.setFileSize(101L);
		file2.setIsGone(true);
		file2.setDirNode(dn);

		em().flush();
	}

	/**
	 * Testing for expected error state of no OneKbHash. TODO: narrow exception expectation.
	 */
	@Test(expected=PersistenceException.class)
	public void fileNodeFailsOnPersistWithoutOneKbHashTest() {
		DirNode dn = new DirNode(null,"root4", null);
		em().persist(dn);

		FileNode file2 = new FileNode();
		em().persist(file2);
		file2.setFileName("file3");
		file2.setOneKbHash(null);
		file2.setFullHash(new Byte[]{3});
		file2.setFileSize(101L);
		file2.setIsGone(true);
		file2.setDirNode(dn);

		em().flush();
	}

	/**
	 * Testing for expected error state of no FullHash. TODO: narrow exception expectation.
	 */
	@Test(expected=PersistenceException.class)
	public void fileNodeFailsOnPersistWithoutFullHashTest() {
		DirNode dn = new DirNode(null,"root5", null);
		em().persist(dn);

		FileNode file2 = new FileNode();
		em().persist(file2);
		file2.setFileName("file4");
		file2.setOneKbHash(new Byte[]{4});
		file2.setFullHash(null);
		file2.setFileSize(101L);
		file2.setIsGone(true);
		file2.setDirNode(dn);

		em().flush();
	}

	/**
	 * Testing for expected error state of no FileSize. TODO: narrow exception expectation.
	 */
	@Test(expected=PersistenceException.class)
	public void fileNodeFailsOnPersistWithoutFileSizeTest() {
		DirNode dn = new DirNode(null,"root6", null);
		em().persist(dn);

		FileNode file2 = new FileNode();
		em().persist(file2);
		file2.setFileName("file5");
		file2.setOneKbHash(new Byte[]{5});
		file2.setFullHash(new Byte[]{6});
		file2.setFileSize(null);
		file2.setIsGone(true);
		file2.setDirNode(dn);

		em().flush();
	}

	/**
	 * Testing for default value propagation for isGone. TODO: narrow exception expectation.
	 */
	@Test
	public void fileNodeUsesDefaultWithoutIsGoneTest() {
		DirNode dn = new DirNode(null,"root7", null);

		FileNode file2 = new FileNode();
		file2.setFileName("file6");
		file2.setOneKbHash(new Byte[]{5});
		file2.setFullHash(new Byte[]{6});
		file2.setFileSize(10L);
		//not setting "IsGone";
		file2.setDirNode(dn);
		em().persist(dn);
		em().persist(file2);
		em().flush();

		FileNode file3 = em().find(FileNode.class, file2.getId());

		assertNotNull("IsGone somehow null!", file3.getIsGone());
		assertFalse("IsGone not set to default!", file3.getIsGone());
	}
}
