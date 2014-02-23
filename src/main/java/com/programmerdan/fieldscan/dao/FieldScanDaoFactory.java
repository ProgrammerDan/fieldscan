package com.programmerdan.fieldscan.dao;

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
	
	private static FieldScanDaoFactory instance = null;

	protected FieldScanDaoFactory() {
		configDao = null;
		processorDao = null;
		fileDao = null;
		dirDao = null;
	}

	public FieldScanDaoFactory getInstance() {
		if (instance == null) {
			instance = new FieldScanDaoFactory();
		}

		return instance;
	}

	public <T> BaseDao<T, ?> getDaoFor(Class<T> daoType) {
		if (daoType.equals(FieldScanConfig.class) ) {
			return getFieldScanConfigDao();
		} else if (daoType.equals(NodeProcessorConfigDao.class) ) {
			return getNodeProcessorConfigDao();
		} else if (daoType.equals(FileNode.class) ) {
			return getFileNodeDao();
		} else if (daoType.equals(DirNode.class) ) {
			return getDirNodeDao();
		} else {
			throw new DaoNotAvailable(daoType);
		}
	}

	public FieldScanConfigDao getFieldScanConfigDao() {
		if (configDao == null) {
			configDao = new FieldScanConfigDaoImpl();
		}
		return configDao;
	}

	public NodeProcessorConfigDao getNodeProcessorConfigDao() {
		if (processorDao == null) {
			processorDao = new NodeProcessorConfigDaoImpl();
		}
		return processorDao;
	}

	public FileNodeDao getFileNodeDao() {
		if (fileDao == null) {
			fileDao = new FileNodeDaoImpl();
		}
		return fileDao;
	}

	public DirNodeDao getDirNodeDao() {
		if (dirDao == null) {
			dirDao = new DirNodeDaoImpl();
		}
		return dirDao;
	}
}
