package com.programmerdan.fieldscan.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.CriteriaQuery;

import java.util.List;

import com.programmerdan.fieldscan.dao.BaseDao;
import com.programmerdan.fieldscan.dao.NoTransactionAvailableException;
import com.programmerdan.fieldscan.dao.NoEntityManagerAssignedException;
import com.programmerdan.fieldscan.dao.FieldScanDaoException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * implementation of base dao using the persistence api. 
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 20, 2014
 *   Basic implementation of Dao using persistence Apis.
 */
public abstract class BaseDaoImpl<T,K> implements BaseDao<T,K> {
	private final static Logger baseLog = LoggerFactory.getLogger(
			BaseDaoImpl.class);

	private EntityManager manager;

	private Class<T> entityType;

	/**
	 * This constructor is a hack to give me type information on 
	 * T, bypassing erasure constraints. It's not a guarantee for
	 * more complex type scenarios, but for my needs it's more than
	 * sufficient.
	 */
	public BaseDaoImpl() {
		this.entityType = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * Returns the parameterized class type that the instance of this dao
	 * works with.
	 *
	 * @return the Class type for T
	 */
	public Class<T> getTypeClass() {
		return entityType;
	}

	/**
	 * Get the assigned EntityManager
	 *
	 * @return {@link EntityManager} as assigned
	 */
	public EntityManager getManager() {
		return this.manager;
	}
	
	/**
	 * Sets the EntityManager for this DAO.
	 *
	 * @param manager assigns an {@link EntityManager}
	 */
	public void setManager(EntityManager manager) {
		this.manager = manager;
	}
	
	/**
	 * Wrapper for transaction. This allows DAO implementations to handle
	 * transactions differently, provided subclasses "behave" and
	 * use this method.
	 *
	 * @param em {@link EntityManager} to use when starting a transaction
	 * @return {@link EntityTransaction} reference to use for closing
	 *   or manipulating the transaction.
	 */
	protected EntityTransaction startTransaction(EntityManager em) {
		if (em == null) {
			throw new NoEntityManagerAssignedException();
		}
		EntityTransaction xact = em.getTransaction();
		if (xact == null) {
			throw new NoTransactionAvailableException();
		}
		xact.begin();
		return xact;
	}

	/**
	 * Again, wrapper for closing the transaction to encapsulate for
	 * overloading.
	 *
	 * @param xact The {@link EntityTransaction} to close.
	 */
	protected void endTransaction(EntityTransaction xact) {
		endTransaction(xact, false);
	}

	/**
	 * Less optimistic version of the endTransaction method, allows
	 * for rollback.
	 *
	 * @param xact the {@link EntityTransaction} to close.
	 * @param failed flag indicating to (true) rollback or (false) commit.
	 */
	protected void endTransaction(EntityTransaction xact, boolean failed) {
		if (failed) {
			xact.rollback();
		} else {
			xact.commit();
		}
	}

	/**
	 * Simple {@link EntityManager#find(Class, Object)} wrapper 
	 * leveraging the generics within this DAO framework.
	 *
	 * @param key The object primary key, for lookup purposes.
	 * @return T type-safe return of the object, null otherwise.
	 */
	public T find(K key) {
		EntityManager em = getManager();
		EntityTransaction xact = startTransaction(em);
		T result = null;
		try {
			result = em.find(this.entityType, key);
		} catch (PersistenceException pe) {
			baseLog.error(pe);
			endTransaction(xact, true);
			throw new FieldScanDaoException(pe);
		}
		endTransaction(xact);
		return result;
	}

	/**
	 * Context aware wrapper of {@link EntityManager#merge(Object)} and
	 * {@link EntityManager#save(Object)} depending on if the object in
	 * question represents a new or already managed Entity.
	 *
	 * @param obj entity to merge or persist
	 */
	public void save(T obj) {
		EntityManager em = getManager();
		EntityTransaction xact = startTransaction(em);
		try {
			if (em.contains(obj)) {
				em.merge(obj);
			} else {
				em.persist(obj);
			}
			em.flush();
		} catch (PersistenceException pe) {
			baseLog.error(pe);
			endTransaction(xact, true);
			throw new FieldScanDaoException(pe);
		}
		endTransaction(xact);
	}
	
	/**
	 * Simple query to get all of a particular type. This is
	 * not recommended for general use, unless the type is a static
	 * definition or very small. Better to write or use a key-based
	 * query.
	 *
	 * @return a {@link List} of all type-safe entities available.
	 */
	public List<T> findAll() {
		EntityManager em = getManager();
		EntityTransaction xact = startTransaction(em);
		List<T> found = null;
		try {
			CriteriaQuery<T> query = em.getCriteriaBuilder()
					.createQuery(entityType);
			query.from(entityType);
			found = em.createQuery(query).getResultList();
		} catch (PersistenceException pe) {
			baseLog.error(pe);
			endTransaction(xact, true);
			throw new FieldScanDaoException(pe);
		}
		endTransaction(xact);
		return found;
	}

	/**
	 * Wrapper of {@link EntityManage#remove(Object)} leveraging
	 * our framework. Also gracefully handles failing to remove
	 * entities not yet persisted.
	 *
	 * @param obj entity to remove
	 * @return true if successful, false if not under management
	 */
	public boolean delete(T obj) {
		EntityManager em = getManager();
		EntityTransaction xact = startTransaction(em);
		try {
			em.remove(obj);
		} catch (IllegalArgumentException iae) {
			baseLog.warn(iae);
			endTransaction(xact, true);
			return false;
		} catch (PersistenceException pe) {
			baseLog.error(pe);
			endTransaction(xact, true);
			throw new FieldScanDaoException(pe);
		}
		endTransaction(xact);
		return true;
	}
}
