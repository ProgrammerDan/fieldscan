package com.programmerdan.fieldscan.dao;

import com.programmerdan.fieldscan.model.FileNode;
import com.programmerdan.fieldscan.model.DirNode;

import java.util.Set;
import java.nio.Path;

/**
 * FileNode DAO, which adds support for find by similarity and find by path.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Inclusion of find by similarity, find by path, and find by DirNode
 */
public interface FileNodeDao implements BaseDao<FileNode,Long>{
	
	public Set<FileNode> findAllBySimilarity(FileNode similar);
	public FileNode findFirstByPath(Path path);
	public Set<FileNode> findAllByPath(Path path);
	public Set<FileNode> findAllByDirNode(DirNode dirNode);
}
