package com.programmerdan.fieldscan.dao;

import java.util.Set;
import javax.persistence.Criteria;
import java.nio.Path;

/**
 * FileNode DAO, which adds support for find by criteria and find by path..
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Inclusion of find by criteria and.find by path, and find by DirNode
 */
public interface FileNodeDao implements BaseDao<FileNode,Long>{
	
	public Set<FileNode> findAllByCriteria(Criteria criteria);
	public FileNode findFirstByPath(Path path);
	public Set<FileNode> findAllByPath(Path path);
	public Set<FileNode> findAllByDirNode(DirNode dirNode);
}


