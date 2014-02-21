package com.programmerdan.fieldscan.dao;

import com.programmerdan.fieldscan.model.FileNode;
import com.programmerdan.fieldscan.model.DirNode;

import java.util.Set;
import java.nio.Path;

/**
 * DirNode DAO, which adds support for find by path.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Inclusion of find by path
 */
public interface DirNodeDao implements BaseDao<DirNode,Long>{
	
	public DirNode findFirstByPath(Path path);
	public Set<DirNode> findAllByPath(Path path);
}


