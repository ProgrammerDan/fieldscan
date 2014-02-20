package com.programmerdan.fieldscan;

import com.programmerdan.fieldscan.model.FileNode;
import com.programmerdan.fieldscan.model.DirNode;
import com.programmerdan.fieldscan.model.BaseNode;
import com.programmerdan.fieldscan.model.FieldScanStatistics;

import com.programmerdan.fieldscan.FieldScanException;
import com.programmerdan.fieldscan.NodeProcessor;

import com.programmerdan.fieldscan.dao.FileNodeDao;
import com.programmerdan.fieldscan.dao.DirNodeDao;
import com.programmerdan.fieldscan.dao.FieldScanStatisticsDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * @param path Starting path
	 * @return {@link FieldScanStatistics} object containing statistics on
	 *   what actions where taken and their results.
	 * @throws {@link FieldScanException} if something unrecoverable happens,
	 *   throws a subclass of FieldScanException.
	 */
	public FieldScanStatistics doScan(String path) throws FieldScanException {
		
	}
}
