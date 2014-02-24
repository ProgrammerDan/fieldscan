package com.programmerdan.fieldscan.dao;

import com.programmerdan.fieldscan.dao.impl.FieldScanConfigDaoImpl;
import com.programmerdan.fieldscan.dao.impl.NodeProcessorConfigDaoImpl;
import com.programmerdan.fieldscan.dao.impl.FileNodeDaoImpl;
import com.programmerdan.fieldscan.dao.impl.DirNodeDaoImpl;

import com.programmerdan.fieldscan.dao.FieldScanConfigDao;
import com.programmerdan.fieldscan.dao.NodeProcessorConfigDao;
import com.programmerdan.fieldscan.dao.FileNodeDao;
import com.programmerdan.fieldscan.dao.DirNodeDao;
import com.programmerdan.fieldscan.dao.BaseDao;

import com.programmerdan.fieldscan.dao.DaoNotAvailableException;

import com.programmerdan.fieldscan.model.FieldScanConfig;
import com.programmerdan.fieldscan.model.NodeProcessorConfig;
import com.programmerdan.fieldscan.model.FileNode;
import com.programmerdan.fieldscan.model.DirNode;

/**
 * Trying something a little different for me and adding another level of
 * abstraction to future proof my design. Instead of dealing with DAO
 * implementations directly, I'll have FieldScan deal with this factory,
 * which will return a DAO based on either the requested object type or
 * explicitly by name.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 22, 2014
 *   Basic DAO Factory implementation
 */
public class FieldScanDaoFactory {

	private FieldScanConfigDao configDao;
	private NodeProcessorConfigDao processorDao;
	private FileNodeDao fileDao;
	private DirNodeDao dirDao;
	private FieldScanStatisticsDao statsDao;
	
	private static FieldScanDaoFactory instance = null;

	/**
	 * Singleton pattern, protected so subclasses can mess with it.
	 */
	protected FieldScanDaoFactory() {
		configDao = null;
		processorDao = null;
		fileDao = null;
		dirDao = null;
		statsDao = null;
	}

	/**
	 * Get or create the singleton instance of this FieldScanDaoFactory.
	 *
	 * @return the FieldScanDaoFactory instance
	 */
	public FieldScanDaoFactory getInstance() {
		if (instance == null) {
			instance = new FieldScanDaoFactory();
		}

		return instance;
	}

	/**
	 * Get a DAO based on an object. This could be useful for NodeProcessor
	 * subclasses if they need access to FileNodes or something similar.
	 *
	 * @param daoType the Class type of the object to get a DAO for.
	 * @return the DAO implementation as the generic {@link BaseDao}.
	 * @throw {@link DaoNotAvailableException} if unable to find a DAO for
	 *   the class type requested.
	 */
	public <T> BaseDao<T, ?> getDaoFor(Class<T> daoType) throws
			DaoNotAvailableException {
		if (daoType.equals(FieldScanConfig.class) ) {
			return getFieldScanConfigDao();
		} else if (daoType.equals(NodeProcessorConfig.class) ) {
			return getNodeProcessorConfigDao();
		} else if (daoType.equals(FileNode.class) ) {
			return getFileNodeDao();
		} else if (daoType.equals(DirNode.class) ) {
			return getDirNodeDao();
		} else if (daoType.equals(FieldScanStatistics.class) ) {
			return getFieldScanStatisticsDao();
		} else {
			throw new DaoNotAvailableException(daoType);
		}
	}

	/**
	 * Gets the FieldScanConfigDao using the standard implementation.
	 *
	 * @return the {@link FieldScanConfigDao} instance.
	 */
	public FieldScanConfigDao getFieldScanConfigDao() {
		if (configDao == null) {
			configDao = new FieldScanConfigDaoImpl();
		}
		return configDao;
	}

	/**
	 * Gets the NodeProcessorConfigDao using the standard implementation.
	 *
	 * @return the {@link NodeProcessorConfigDao} instance.
	 */
	public NodeProcessorConfigDao getNodeProcessorConfigDao() {
		if (processorDao == null) {
			processorDao = new NodeProcessorConfigDaoImpl();
		}
		return processorDao;
	}

	/**
	 * Gets the FileNodeDao using the standard implementation.
	 *
	 * @return the {@link FileNodeDao} instance.
	 */
	public FileNodeDao getFileNodeDao() {
		if (fileDao == null) {
			fileDao = new FileNodeDaoImpl();
		}
		return fileDao;
	}

	/**
	 * Gets the DirNodeDao using the standard implementation.
	 *
	 * @return the {@link DirNodeDao} instance.
	 */
	public DirNodeDao getDirNodeDao() {
		if (dirDao == null) {
			dirDao = new DirNodeDaoImpl();
		}
		return dirDao;
	}

	/**
	 * Gets the FieldScanStatisticsDao using the standard implementation.
	 *
	 * @return the {@link FieldScanStatisticsDao} instance.
	 */
	public FieldScanStatisticsDao getFieldScanStatisticsDao() {
		if (statsDao == null) {
			statsDao = new FieldScanStatisticsDaoImpl();
		}
		return statsDao;
	}
}
