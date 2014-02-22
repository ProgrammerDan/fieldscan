package com.programmerdan.fieldscan.dao.impl;

import com.programmerdan.fieldscan.dao.NodeProcessorConfigDao;
import com.programmerdan.fieldscan.dao.impl.BaseDaoImpl;
import com.programmerdan.fieldscan.model.NodeProcessorConfig;

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
 * This is no exception, and implements the FindByName. It also serves
 * as a learning vehicle for me and true understanding of CriteriaBuilder's
 * nuances. So bear with me.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
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
			EntityType<NodeProcessorConfig> NodeProcessorConfig_ = mm.entity(
					NodeProcessorConfig.class);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<T> query = cb.createQuery(NodeProcessorConfig.class);
			Root<NodeProcessorConfig> fsc = query.from(NodeProcessorConfig.class);
			query.where(cb.equals(fsc.get(NodeProcessorConfig_.baseConfig), config));

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

