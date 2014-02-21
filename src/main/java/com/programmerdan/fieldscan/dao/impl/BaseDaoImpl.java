package com.programmerdan.fieldscan.dao.impl;

import javax.persistence.EntityManager;
import java.util.Set;
import com.programmerdan.fieldscan.dao.BaseDao;
import com.slf4j.LoggerFactory;
import com.slf4j.Logger;

/**
 * implementation of base dao using the persistence api. 
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Basic implementation of Dao using persistence Apis.
 */
public abstract BaseDaoImpl<T,K> implements BaseDao<T,K> {
	private Logger log = LoggerFactory.getLogger(BaseDaoImpl.class);

	private EntityManager manager;

	public EntityManager getManager() {
		return this.manager;
	}
	
	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	public T find(K key) {
		EntityManager em = getManager();
		EntityTransaction xact = em.getTransaction();
		xact.begin();
		T result = em.find(new Class<T>(), key);
		xact.close();
		return result;
	}

	public boolean save(T obj) {
		EntityManager em = startTransaction();
		if (em.contains(obj)) {
			em.merge(obj);
		} else {
			em.persist(obj);
		}	
		endTransaction();
		return true;
	}
	public Set<T> findAll();
	public boolean delete(T obj);

}
