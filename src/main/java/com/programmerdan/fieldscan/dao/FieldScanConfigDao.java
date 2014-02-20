package com.programmerdan.fieldscan.dao;

/**
 * FieldScanConfig DAO, which adds support for find by name.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Inclusion of find by name.
 */
public interface FieldScanConfigDao implements BaseDao<FieldScanConfig,Long>{
	
	public FieldScanConfig findByName(String name);
}

