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
}
