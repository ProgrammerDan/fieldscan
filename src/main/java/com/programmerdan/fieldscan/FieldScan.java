package com.programmerdan.fieldscan;

import com.programmerdan.fieldscan.model.FileNode;
import com.programmerdan.fieldscan.model.DirNode;
import com.programmerdan.fieldscan.model.BaseNode;
import com.programmerdan.fieldscan.model.FieldScanStatistics;
import com.programmerdan.fieldscan.model.FieldScanConfig;

import com.programmerdan.fieldscan.FieldScanException;
import com.programmerdan.fieldscan.RootPathInvalid;
import com.programmerdan.fieldscan.NodeProcessor;

import com.programmerdan.fieldscan.dao.FieldScanDaoFactory;
import com.programmerdan.fieldscan.dao.FileNodeDao;
import com.programmerdan.fieldscan.dao.DirNodeDao;
import com.programmerdan.fieldscan.dao.FieldScanStatisticsDao;
import com.programmerdan.fieldscan.dao.FieldScanConfigDao;
import com.programmerdan.fieldscan.dao.NodeProcessorConfigDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentHashMap;

import java.nio.file.FileSystems;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Basic FieldScan functionality class. Basically, it hosts a 
 * method that will, based on a configuration in database and a
 * starting path, scan through the path's descendents recursively,
 * keeping track of its progress through {@link FileNode} and
 * {@link DirNode} objects. All potential {@link DirNode} objects
 * that are parents of the starting path will be created as well,
 * for completeness.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 16, 2014
 *   Initial version. Implements basic functionality as promised.
 */
public class FieldScan {

	private static final Logger log = LoggerFactory.getLogger(
			FieldScan.class);

	private EntityManagerFactory persistenceFactory;

	private FileNodeDao fileDao;
	private DirNodeDao dirDao;
	private FieldScanStatisticsDao statsDao;
	private FieldScanConfigDao configDao;
	private NodeProcessorConfigDao processorDao;

	private NodeProcessor rootProcessor;

	private Map<String, NodeProcessor> processorRegistry;

	/**
	 * Setup method that does some configuration of FieldScan on the
	 * downlow. It makes sure the database connection exists and that
	 * the configuration mentioned exists.
	 *
	 * It then attempts to load the root processor, and register
	 * all the rest. Note this method can be called repeatedly, so
	 * as to allow dynamic reconfiguration of a FieldScan process while
	 * running. Could be useful for future services based implementations
	 * where a configuration could change and it's desirable to refresh
	 * FieldScan without restarting it or its host container.
	 *
	 * @param configName the name of the configuration to look up.
	 * @return True if able to set up without error, False otherwise.
	 * @throws FieldScanSetupException if there is a problem.
	 */
	public boolean setup(String configName) throws FieldScanSetupException {
		try {
			if (persistenceFactory != null) {
				persistenceFactory = null; // throw it away
			}
			persistenceFactory = Persistence.createEntityManagerFactory("com.programmerdan.fieldscan.jpa");
		} catch (IllegalStateException ise) {
			log.error("Failed to set up FieldScan", ise);
			throw new FieldScanSetupException(ise);
		}
		processorRegistry = new ConcurrentHashMap<String, NodeProcessor>();

		FieldScanDaoFactory daoFactory = FieldScanDaoFactory.getInstance();
		this.configDao = daoFactory.getFieldScanConfigDao();
		this.statsDao = daoFactory.getFieldScanStatisticsDao();
		this.fileDao = daoFactory.getFileNodeDao();
		this.dirDao = daoFactory.getDirNodeDao();
		this.processorDao = daoFactory.getNodeProcessorConfigDao();

		return true;
	}


	/**
	 * Do Scan is the whole point of this class. It's basically 
	 * a stack-based file system traversal mechanism, with a stack
	 * of processors that deal with a File object and FileNode object
	 * or File object and DirNode object. Processors can be recursive,
	 * meaning that one processor could add others. For instance,
	 * a file processor that detects file type could in turn add
	 * other processors that do feature extraction, etc. to better
	 * determine duplication later.
	 *
	 * Some more details on the core of this method:
	 * This is a very simple algorithm. Basically, while there
	 * are paths/files to process, add the root processor.
	 * Then, execute the processors. Processors have access to
	 * the stack and queue, so they can add more processors to the
	 * processing of a particular file. They can also generate more
	 * files by adding them to the stack.
	 * 
	 * @param path Starting path
	 * @return {@link FieldScanStatistics} object containing statistics on
	 *   what actions where taken and their results.
	 * @throws {@link FieldScanException} if something unrecoverable happens,
	 *   throws a subclass of FieldScanException.
	 */
	public FieldScanStatistics doScan(String path) throws FieldScanException {
		FieldScanStatistics stats = new FieldScanStatistics();
		// Make sure the path is valid before we go any further.
		if (path == null || path.trim().equals("")) {
			throw new RootPathInvalid(path);
		}
		Path firstPath = null;
		try {
			firstPath = FileSystems.getDefault().getPath(path);
		} catch (InvalidPathException ipe) {
			throw new RootPathInvalid(path, ipe);
		}
		if (firstPath == null) {
			throw new RootPathInvalid(path);
		}

		// Using concurrent linked queues to allow more complex, multi-threaded
		// implementations in the future.
		ConcurrentLinkedQueue<NodeProcessor> processorQueue =
				new ConcurrentLinkedQueue<NodeProcessor>();
		ConcurrentLinkedDeque<Path> fileStack =
				new ConcurrentLinkedDeque<Path>();

		if (!fileStack.offerFirst(firstPath)) {
			throw new FieldScanException("Exceeded path stack depth.");
		}

		while (fileStack.peekFirst() != null) {
			Path nextFile = fileStack.pollFirst();

			// first things first. Start with root processor.
			if (!processorQueue.offer(rootProcessor)) {
				throw new FieldScanException("Exceeded processing queue depth.");
			}

			while (processorQueue.peek() != null) {
				NodeProcessor nextProcessor = processorQueue.poll();

				nextProcessor.process(fileStack, processorQueue, this);
			}
		}

		// All Done.
		return stats;
	}
}
