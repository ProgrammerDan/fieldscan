package com.programmerdan.fieldscan.dao;

import com.programmerdan.fieldscan.model.FileNode;
import com.programmerdan.fieldscan.model.DirNode;

import java.util.List;

/**
 * FileNode DAO, which adds support for find by similarity and find by DirNode.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 20, 2014
 *   Inclusion of find by similarity, find by DirNode and by Filename
 */
public interface FileNodeDao extends BaseDao<FileNode,Long>{
	
	public List<FileNode> findAllBySimilarity(FileNode similar);
	public List<FileNode> findAllByDirNode(DirNode dirNode);
	public List<FileNode> findAllByDirNodeAndFileName(String name,
			DirNode dirNode);
}

