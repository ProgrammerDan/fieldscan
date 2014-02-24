package com.programmerdan.fieldscan.dao;

import java.util.List;

/**
 * NodeProcessorConfig DAO, which adds support for find all by {@link FieldScanConfig}.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 20, 2014
 *   Inclusion of find all by FieldScanConfig
 */
public interface NodeProcessorConfigDao extends BaseDao<NodeProcessorConfig,Long> {
	
	public List<NodeProcessorConfig> findAllByFieldScanConfig(FieldScanConfig config);
}
