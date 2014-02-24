package com.programmerdan.fieldscan.dao.impl;

import com.programmerdan.fieldscan.dao.FieldScanDaoException;
import com.programmerdan.fieldscan.dao.NodeProcessorConfigDao;
import com.programmerdan.fieldscan.dao.impl.BaseDaoImpl;
import com.programmerdan.fieldscan.model.NodeProcessorConfig;
import com.programmerdan.fieldscan.model.NodeProcessorConfig_;
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

import java.util.List;

/**
 * This DAO pattern does a cross implementation and extension of the
 * Base types, and implements just those extra methods as necessary.
 * This is no exception, and implements the FindByFieldScanConfig. 
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT Febraury 20, 2014
 *   Basic implementation of Dao using persistence Apis.
 */
public class NodeProcessorConfigDaoImpl extends BaseDaoImpl<NodeProcessorConfig, Long>
		implements NodeProcessorConfigDao {
	
	private static final Logger log = LoggerFactory.getLogger(
			NodeProcessorConfigDaoImpl.class);

	public NodeProcessorConfigDaoImpl() {
		super();
	}

	/**
	 * This method, just finds the full set of NodeProcessorConfig associated
	 * with the given FieldScanConfig.
	 *
	 * @param fieldScanConfig The FieldScanConfig to use as search param.
	 * @return a List of NodeProcessorConfig elements associated with the given FieldScanConfig.
	 */
	public List<NodeProcessorConfig> findAllByFieldScanConfig(FieldScanConfig config) {
		EntityManager em = getManager();
		EntityTransaction xact = startTransaction(em);
		List<NodeProcessorConfig> found = null;
		try {
			Metamodel mm = em.getMetamodel();
			//EntityType<NodeProcessorConfig> NodeProcessorConfig_ = mm.entity(
			//		NodeProcessorConfig.class);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<NodeProcessorConfig> query =
					cb.createQuery(NodeProcessorConfig.class);
			Root<NodeProcessorConfig> fsc = query.from(NodeProcessorConfig.class);
			query.where(cb.equal(fsc.get(NodeProcessorConfig_.baseConfig), config));

			found = em.createQuery(query).getResultList();
		} catch (PersistenceException pe) {
			log.error("Could not find all by field scan config", pe);
			endTransaction(xact, true);
			throw new FieldScanDaoException(pe);
		}
		endTransaction(xact);
		return found;
	}
}

