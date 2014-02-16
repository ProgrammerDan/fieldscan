package com.programmerdan.fieldscan.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

import java.sql.Date;

@Entity
@Table(name="statistics")
public class FieldScanStatistics implements Serializable {

	/**
	 * Internal serial ID to allow other methods of storage besides
	 * JDBC.
	 */
	private static final long serialVersionUID = 71375247003;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id")
	private Long id;

	@Column(name="scan_begin", nullable=false, 
			columnDefinition="scan_begin TIMESTAMP NOT NULL DEFAULT now()")
	private Date scanBegin;

	@Column(name="scan_end", columnDefinition="scan_end TIMESTAMP")
	private Date scanEnd;

	@Column(name="files_processed", nullable=false)
	private Long filesProcessed;

	@Column(name="total_file_processing_time", nullable=false)
	private Long totalFileProcessingTime;

	@Column(name="dirs_processed", nullable=false)
	private Long dirsProcessed;
	
	@Column(name="total_dir_processing_time", nullable=false)
	private Long totalDirProcessingTime;

	@Column(name="count_file_nodes_added", nullable=false)
	private Long countFileNodesAdded;

	@Column(name="count_dir_nodes_added", nullable=false)
	private Long countDirNodesAdded;

	@Column(name="count_file_nodes_gone", nullable=false)
	private Long countFileNodesGone;

	@Column(name="count_dir_nodes_gone", nullable=false)
	private Long countDirNodesGone;

