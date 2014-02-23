package com.programmerdan.fieldscan.dao.impl;

import com.programmerdan.fieldscan.dao.DirNodeDao;
import com.programmerdan.fieldscan.dao.impl.BaseDaoImpl;
import com.programmerdan.fieldscan.model.DirNode;
import com.programmerdan.fieldscan.model.FieldScanConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
			CriteriaQuery<T> query = cb.createQuery(DirNode.class);
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
	 * This method just finds the Set of DirNodes closest to the given
	 * DirNode. The given DirNode is specifically excluded from the
	 * output set.
	 *
	 * TODO: Rewrite this. Looks like I'll probably have to iterate over
	 *   the path elements.
	 *
	 * @param path the Path to use for selecting.
	 * @return a DirNode that exists along the given path, or null.
	 */
	public DirNode findFirstByPath(Path path) {
		EntityManager em = getManager();
		EntityTransaction xact = startTransaction(em);
		List<DirNode> found = null;
		try {
			Metamodel mm = em.getMetamodel();
			EntityType<DirNode> DirNode_ = mm.entity(DirNode.class);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<T> query = cb.createQuery(DirNode.class);
			Root<DirNode> fsc = query.from(DirNode.class);
			query.where(cb.equal(fsc.get(DirNode_.fullHash),
								 fileNode.getFullHash())
				    .or(
						cb.and(
							cb.equal(fsc.get(DirNode_.oneKbHash), 
									 fileNode.getOneKbHash()),
							cb.equal(fsc.get(DirNode_.fileSize),
									 fileNode.getFileSize()))
						)
					.and(
						cb.notEqual(fsc.get(DirNode_.id),
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
}



