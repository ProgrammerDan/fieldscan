package com.programmerdan.fieldscan.dao;

import com.programmerdan.fieldscan.model.FieldScanConfig;

/**
 * FieldScanConfig DAO, which adds support for find by name.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 20, 2014
 *   Inclusion of find by name.
 */
public interface FieldScanConfigDao extends BaseDao<FieldScanConfig,Long>{
	
	public FieldScanConfig findByName(String name);
}

