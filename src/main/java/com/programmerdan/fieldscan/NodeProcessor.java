package com.programmerdan.fieldscan;

import java.util.Deque;
import java.util.Queue;

import java.nio.file.Path;

/**
 * Basic implementation of the interface NodeProcessor. More will
 * follow, including a Factory of some kind. We'll see.
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 24, 2014
 */
public interface NodeProcessor {
	
	public void process( Deque<Path> paths, Queue<NodeProcessor> queue,
			FieldScan fieldScan );
}
