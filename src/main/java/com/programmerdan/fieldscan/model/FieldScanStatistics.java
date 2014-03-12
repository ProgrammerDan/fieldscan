package com.programmerdan.fieldscan.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

import java.sql.Date;

import java.io.Serializable;

/**
 * Basic statistics bean for executions of {@link FieldScan#doScan}
 * 
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT February 17, 2014
 *   Basic version, with simple statistics.
 */
@Entity
@Table(name="statistics")
public class FieldScanStatistics implements Serializable {

	/**
	 * Internal serial ID to allow other methods of storage besides
	 * JDBC.
	 */
	private static final long serialVersionUID = 71375247003L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id")
	private Long id;

	@Column(name="scan_begin", nullable=false, 
			columnDefinition="TIMESTAMP DEFAULT now()")
	private Date scanBegin;

	@Column(name="scan_end", columnDefinition="TIMESTAMP")
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

	public FieldScanStatistics() {
		id = null;
		scanBegin = null;
		scanEnd = null;
		filesProcessed = null;
		totalFileProcessingTime = null;
		dirsProcessed = null;
		totalDirProcessingTime = null;
		countFileNodesAdded = null;
		countDirNodesAdded = null;
		countFileNodesGone = null;
		countDirNodesGone = null;
	}

	public FieldScanStatistics(Long id, Date scanBegin, Date scanEnd,
			Long filesProcessed, Long totalFileProcessingTime,
			Long dirsProcessed, Long totalDirProcessingTime,
			Long countFileNodesAdded, Long countDirNodesAdded,
			Long countFileNodesGone, Long countDirNodesGone) {
		this.id = id;
		this.scanBegin = scanBegin;
		this.scanEnd = scanEnd;
		this.filesProcessed = filesProcessed;
		this.totalFileProcessingTime = totalFileProcessingTime;
		this.dirsProcessed = dirsProcessed;
		this.totalDirProcessingTime = totalDirProcessingTime;
		this.countFileNodesAdded = countFileNodesAdded;
		this.countDirNodesAdded = countDirNodesAdded;
		this.countFileNodesGone = countFileNodesGone;
		this.countDirNodesGone = countDirNodesGone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getScanBegin() {
		return scanBegin;
	}

	public void setScanBegin(Date scanBegin) {
		this.scanBegin = scanBegin;
	}

	public Date getScanEnd() {
		return scanEnd;
	}

	public void setScanEnd(Date scanEnd) {
		this.scanEnd = scanEnd;
	}

	public Long getFilesProcessed() {
		return filesProcessed;
	}

	public void setFilesProcessed(Long fileProcessed) {
		this.filesProcessed = filesProcessed;
	}

	public Long incFilesProcessed() {
		if (this.filesProcessed != null) {
			this.filesProcessed++;
		} else { 
			this.filesProcessed = 0L;
		}
		return this.filesProcessed;
	}

	public Long getTotalFileProcessingTime() {
		return totalFileProcessingTime;
	}

	public void setTotalFileProcessingTime(Long totalFileProcessingTime) {
		this.totalFileProcessingTime = totalFileProcessingTime;
	}

	public Long getDirsProcessed() {
		return dirsProcessed;
	}

	public void setDirsProcessed(Long dirsProcessed) {
		this.dirsProcessed = dirsProcessed;
	}

	public Long incDirsProcessed() {
		if (this.dirsProcessed != null) {
			this.dirsProcessed++;
		} else {
			this.dirsProcessed = 0L;
		}
		return this.dirsProcessed;
	}

	public Long getTotalDirProcessingTime() {
		return totalDirProcessingTime;
	}

	public void setTotalDirProcessingTime(Long totalDirProcessingTime) {
		this.totalDirProcessingTime = totalDirProcessingTime;
	}

	public Long getCountFileNodesAdded() {
		return countFileNodesAdded;
	}

	public void setCountFileNodesAdded(Long countFileNodesAdded) {
		this.countFileNodesAdded = countFileNodesAdded;
	}

	public Long getCountDirNodesAdded() {
		return countDirNodesAdded;
	}

	public void setCountDirNodesAdded(Long countDirNodesAdded) {
		this.countDirNodesAdded = countDirNodesAdded;
	}

	public Long getCountFileNodesGone() {
		return countFileNodesGone;
	}

	public void setCountFileNodesGone(Long countFileNodesGone) {
		this.countFileNodesGone = countFileNodesGone;
	}

	public Long getCountDirNodesGone() {
		return countDirNodesGone;
	}
	
	public void setCountDirNodesGone(Long countDirNodesGone) {
		this.countDirNodesGone = countDirNodesGone;
	}
}
