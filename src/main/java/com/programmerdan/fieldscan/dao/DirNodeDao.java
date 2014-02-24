package com.programmerdan.fieldscan.dao;

import com.programmerdan.fieldscan.model.FileNode;
import com.programmerdan.fieldscan.model.DirNode;

import java.util.List;
import java.nio.file.Path;

/**
 * DirNode DAO, which adds support for find by path.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 20, 2014
 *   Inclusion of find by path
 */
public interface DirNodeDao extends BaseDao<DirNode,Long>{
	
	public List<DirNode> findAllChildren(DirNode dirNode);
	public DirNode findRootDirByDirectoryName(String directoryName);
	public DirNode findFirstByPath(Path path);
}


