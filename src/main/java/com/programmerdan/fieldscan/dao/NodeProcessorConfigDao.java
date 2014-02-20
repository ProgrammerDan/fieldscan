package com.programmerdan.fieldscan.dao;

import java.util.Set;

/**
 * NodeProcessorConfig DAO, which adds support for find all by {@link FieldScanConfig}.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT
 *   Inclusion of find all by FieldScanConfig
 */
public interface NodeProcessorConfigDao implements BaseDao<NodeProcessorConfig,Long>{
	
	public Set<NodeProcessorConfig> findAllByFieldScanConfig(FieldScanConfig config);
}


