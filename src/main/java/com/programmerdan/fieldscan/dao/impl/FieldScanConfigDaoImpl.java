package com.programmerdan.fieldscan.dao.impl;

import com.programmerdan.fieldscan.dao.FieldScanConfigDao;
import com.programmerdan.fieldscan.dao.impl.BaseDaoImpl;
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
 * This is no exception, and implements the FindByName. It also serves
 * as a learning vehicle for me and true understanding of CriteriaBuilder's
 * nuances. So bear with me.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Basic implementation of Dao using persistence Apis.
 */
public class FieldScanConfigDaoImpl extends BaseDaoImpl<FieldScanConfig, Long>
		implements FieldScanConfigDao {
	
	private static final Logger log = LoggerFactory.getLogger(
			FieldScanConfigDaoImpl.class);

	public FieldScanConfigDaoImpl() {
		super();
	}

	/**
	 * Trying and not likely this query builder nonsense. HQL is definitely
	 * more direct, but if I can master this it could be less subject
	 * to evaluation ambiguity. No idea which is more performant,
	 * I'll have to do that analysis at some point.
	 *
	 * But for this method, just finds the FieldScanConfig with the
	 * given name.
	 *
	 * @param name The FieldScanConfig name to retrieve
	 * @return a FieldScanConfig if found
	 */
	public FieldScanConfig findByName(String name) {
		EntityManager em = getManager();
		EntityTransaction xact = startTransaction(em);
		FieldScanConfig found = null;
		try {
			Metamodel mm = em.getMetamodel();
			EntityType<FieldScanConfig> FieldScanConfig_ = mm.entity(
					FieldScanConfig.class);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<T> query = cb.createQuery(FieldScanConfig.class);
			Root<FieldScanConfig> fsc = query.from(FieldScanConfig.class);
			query.where(cb.equals(fsc.get(FieldScanConfig_.configName), name));

			found = em.createQuery(query).getResultList();
		} catch (PersistenceException pe) {
			baseLog.error(pe);
			endTransaction(xact, true);
			throw new FieldScanDaoException(pe);
		}
		endTransaction(xact);
		return found;
	}
}
