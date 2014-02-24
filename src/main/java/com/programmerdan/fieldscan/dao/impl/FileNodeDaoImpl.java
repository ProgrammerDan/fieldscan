package com.programmerdan.fieldscan.dao.impl;

import com.programmerdan.fieldscan.dao.FieldScanDaoException;
import com.programmerdan.fieldscan.dao.FileNodeDao;
import com.programmerdan.fieldscan.dao.impl.BaseDaoImpl;
import com.programmerdan.fieldscan.model.FileNode;
import com.programmerdan.fieldscan.model.DirNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.EntityType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.PersistenceException;

/**
 * This DAO pattern does a cross implementation and extension of the
 * Base types, and implements just those extra methods as necessary.
 * This is no exception, and implements the Find all by DirNode and
 * Find all by Similarity, which is a first-stab at duplication
 * detection. 
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 21, 2014
 *   Basic implementation of Dao using persistence Apis.
 */
public class FileNodeDaoImpl extends BaseDaoImpl<FileNode, Long>
		implements FileNodeDao {
	
	private static final Logger log = LoggerFactory.getLogger(
			FileNodeDaoImpl.class);

	/**
	 * Standard constructor.
	 */
	public FileNodeDaoImpl() {
		super();
	}

	/**
	 * This method just finds the full set of FileNode mapped to
	 * the given DirNode. Includes prior FileNodes marked "Gone".
	 *
	 * @param dirNode the DirNode whose files to find
	 * @return a List of FileNode elements.
	 */
	public List<FileNode> findAllByDirNode(DirNode dirNode) {
		EntityManager em = getManager();
		EntityTransaction xact = startTransaction(em);
		List<FileNode> found = null;
		try {
			Metamodel mm = em.getMetamodel();
			EntityType<FileNode> FileNode_ = mm.entity(FileNode.class);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<FileNode> query = cb.createQuery(FileNode.class);
			Root<FileNode> fsc = query.from(FileNode.class);
			query.where(cb.equal(fsc.get(FileNode_.dirNode), dirNode));

			found = em.createQuery(query).getResultList();
		} catch (PersistenceException pe) {
			log.error(pe);
			endTransaction(xact, true);
			throw new FieldScanDaoException(pe);
		}
		endTransaction(xact);
		return found;
	}

	/**
	 * This method just finds the Set of FileNodes closest to the given
	 * FileNode. The given FileNode is specifically excluded from the
	 * output set.
	 *
	 * @param fileNode the FileNode to use for similarity checking.
	 * @return a List of FileNode elements similar to the given fileNode.
	 */
	public List<FileNode> findAllBySimilarity(FileNode fileNode ) {
		EntityManager em = getManager();
		EntityTransaction xact = startTransaction(em);
		List<FileNode> found = null;
		try {
			Metamodel mm = em.getMetamodel();
			EntityType<FileNode> FileNode_ = mm.entity(FileNode.class);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<FileNode> query = cb.createQuery(FileNode.class);
			Root<FileNode> fsc = query.from(FileNode.class);
			query.where(cb.equal(fsc.get(FileNode_.fullHash),
								 fileNode.getFullHash())
				    .or(
						cb.and(
							cb.equal(fsc.get(FileNode_.oneKbHash), 
									 fileNode.getOneKbHash()),
							cb.equal(fsc.get(FileNode_.fileSize),
									 fileNode.getFileSize()))
						)
					.and(
						cb.notEqual(fsc.get(FileNode_.id),
									fileNode.getId())
						)
					);
			found = em.createQuery(query).getResultList();
		} catch (PersistenceException pe) {
			log.error(pe);
			endTransaction(xact, true);
			throw new FieldScanDaoException(pe);
		}
		endTransaction(xact);
		return found;
	}

	/**
	 * This method finds all files based on a directory node and a file name.
	 *
	 * @param dirNode the DirNode that's parent to this file
	 * @param name the file name
	 * @return the FileNode that matches given conditions.
	 */
	public List<FileNode> findAllByDirNodeAndFileName(String name, 
			DirNode dirNode) {
		EntityManager em = getManager();
		EntityTransaction xact = startTransaction(em);
		List<FileNode> found = null;
		try {
			Metamodel mm = em.getMetamodel();
			EntityType<FileNode> FileNode_ = mm.entity(FileNode.class);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<FileNode> query = cb.createQuery(FileNode.class);
			Root<FileNode> fsc = query.from(FileNode.class);
			query.where(
					cb.and(
						cb.equal(fsc.get(FileNode_.dirNode), dirNode),
						cb.equal(fsc.get(FileNode_.fileName), name)
					)
				);
			found = em.createQuery(query).getResultList();
		} catch (PersistenceException pe) {
			log.error(pe);
			endTransaction(xact, true);
			throw new FieldScanDaoException(pe);
		}
		endTransaction(xact);
		return found;
	}
}


