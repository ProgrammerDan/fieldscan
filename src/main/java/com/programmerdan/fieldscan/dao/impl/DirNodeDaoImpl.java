package com.programmerdan.fieldscan.dao.impl;

import com.programmerdan.fieldscan.dao.FieldScanDaoException;
import com.programmerdan.fieldscan.dao.DirNodeDao;
import com.programmerdan.fieldscan.dao.impl.BaseDaoImpl;
import com.programmerdan.fieldscan.model.DirNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import java.nio.file.Path;

import java.io.IOError;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.EntityType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.PersistenceException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 * This DAO pattern does a cross implementation and extension of the
 * Base types, and implements just those extra methods as necessary.
 * This is no exception, and implements the Find all by DirNode and
 * Find all by Similarity, which is a first-stab at duplication
 * detection. 
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Basic implementation of Dao using persistence Apis.
 */
public class DirNodeDaoImpl extends BaseDaoImpl<DirNode, Long>
		implements DirNodeDao {
	
	private static final Logger log = LoggerFactory.getLogger(
			DirNodeDaoImpl.class);

	/**
	 * Standard constructor.
	 */
	public DirNodeDaoImpl() {
		super();
	}

	/**
	 * This method just finds the full set of DirNode mapped to
	 * the given DirNode. Includes prior DirNodes marked "Gone".
	 *
	 * @param dirNode the DirNode to consider as parent node.
	 * @return a List of DirNode elements.
	 */
	public List<DirNode> findAllChildren(DirNode dirNode) {
		EntityManager em = getManager();
		EntityTransaction xact = startTransaction(em);
		List<DirNode> found = null;
		try {
			Metamodel mm = em.getMetamodel();
			EntityType<DirNode> DirNode_ = mm.entity(DirNode.class);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DirNode> query = cb.createQuery(DirNode.class);
			Root<DirNode> fsc = query.from(DirNode.class);
			query.where(cb.equal(fsc.get(DirNode_.parentDir), dirNode));

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
	 * Finds a root directory (DirNode without a parent DirNode) that
	 * has the given directory name.
	 *
	 * @param directoryName the name of the directory with no parent dir
	 * @return a matching DirNode.
	 */
	public DirNode findRootDirByDirectoryName(String directoryName) {
		EntityManager em = getManager();
		EntityTransaction xact = startTransaction(em);
		DirNode found = null;
		try {
			Metamodel mm = em.getMetamodel();
			EntityType<DirNode> DirNode_ = mm.entity(DirNode.class);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DirNode> query = cb.createQuery(DirNode.class);
			Root<DirNode> fsc = query.from(DirNode.class);
			query.where(
				cb.and(
					cb.equal(fsc.get(DirNode_.directoryName), directoryName),
					cb.isNull(fsc.get(DirNode_.parentDir))
					)
				);
			found = em.createQuery(query).getSingleResult();
		} catch (PersistenceException pe) {
			log.error(pe);
			endTransaction(xact, true);
			throw new FieldScanDaoException(pe);
		}
		endTransaction(xact);
		return found;
	}

	/**
	 * Finds the first DirNode that matches the given {@link Path}, provided
	 * there is an accessible DirNode for every element within the Path.
	 *
	 * This is a bit complex, and not efficient, but should work in principle.
	 *
	 * @param path the Path to use for selecting.
	 * @return a DirNode that exists along the given path, or null.
	 */
	public DirNode findFirstByPath(Path path) {
		Path truePath = null;
		try {
			truePath = path.normalize().toAbsolutePath;
			truePath = truePath.toRealPath(LinkOption.NOFOLLOW_LINKS);
		} catch (IOError ioe) {
			log.error(ioe);
			throw new FieldScanDaoException(
					"File System unavailable, cannot resolve path", ioe);
		} catch (SecurityException se) {
			log.warn(se);
			throw new FieldScanDaoException(
					"Insufficient Security to resolve path", se);
		}
		Path rootPath = truePath.getRoot();
		if (rootPath == null) {
			throw new FieldScanDaoException("No root path resolvable");
		}

		EntityManager em = getManager();
		EntityTransaction xact = startTransaction(em);
		DirNode found = null;
		DirNode curDir = findRootDirByDirectoryName(rootPath.toString());

		try {
			Metamodel mm = em.getMetamodel();
			EntityType<DirNode> DirNode_ = mm.entity(DirNode.class);
			CriteriaBuilder cb = em.getCriteriaBuilder();

			CriteriaQuery<DirNode> query = null;
			Root<DirNode> fsc = null;

			Iterator<Path> pathIter = truePath.iterator();
			Path curPath = null;
			while ( pathIter.hasNext() ) {
				curPath = pathIter.next();

				query = cb.createQuery(DirNode.class);
				fsc = query.from(DirNode.class);

				query.where(
					cb.and(
						cb.equal(fsc.get(DirNode_.parentDir), curDir),
						cb.equal(fsc.get(DirNode_.directoryName),
								 curPath.toString())
						)
					);
				try {
					curDir = em.createQuery(query).getSingleResult();
				} catch (NoResultException nre) {
					log.warn(truePath.toString() + " not in persistence store");
					endTransaction(xact);
					return null;
				} catch (NonUniqueResultException nure) {
					log.warn(truePath.toString() + 
							" could not be uniquely resolved");
					endTransaction(xact);
					return null;
				}
			}

			found = curDir;
		} catch (PersistenceException pe) {
			log.error(pe);
			endTransaction(xact, true);
			throw new FieldScanDaoException(pe);
		}
		endTransaction(xact);
		return found;
	}
}



