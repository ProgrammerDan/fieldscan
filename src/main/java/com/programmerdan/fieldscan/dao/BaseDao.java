package com.programmerdan.fieldscan.dao;

import java.util.List;

/**
 * Simple DAO pattern to keep my data access organized.
 * Probably overkill for this project, so I might strip
 * it back out. We will see!
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT Febraury 20, 2014
 *   Basic CRUD and find implementation.
 */
public interface BaseDao<T,K> {
	
	public T find(K key);
	public void save(T obj);
	public List<T> findAll();
	public boolean delete(T obj);
}
