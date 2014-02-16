package com.programmerdan.fieldscan.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

/**
 * Basic file node. Extensions will probably just be additions to this file.
 * Not sure yet.
 *
 * TODO: Comment appropriately
 *
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT Feb 16, 2014
 *   Initial version, sets up a simple node for future comparisons.
 */
@Entity
@Table(name="file_node")
public class FileNode implements BaseNode, Serializable {

	/**
	 * Internal serial ID to allow other methods of storage besides
	 * JDBC.
	 */
	private static final long serialVersionUID = 71375247001;

	@Id
	@GeneratedValue(strategy=SEQUENCE)
	@Column(name="id")
	private Long id;

	@Column(name="file_name", nullable=false)
	private String fileName;

	@Column(name="one_kb_hash", nullable=false, columnDefinition="one_kb_hash BYTEA NOT NULL")
	private Byte[] oneKbHash;

	@Column(name="full_hash", nullable=false, columnDefinition="full_hash BYTEA NOT NULL")
	private Byte[] fullHash;

	@Column(name="file_size", nullable=false)
	private Long fileSize;

	@Column(name="is_gone", nullable=false, columnDefinition="is_gone BOOLEAN NOT NULL DEFAULT false")
	private Boolean isGone;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="dir_node_id", table="dir_node", referencedColumnName="id",
			nullable=false);
	private DirNode dirNode;

	public FileNode() {
		this.id = null;
		this.fileName = null;
		this.oneKbHash = null;
		this.fullHash = null;
		this.fileSize = null;
		this.isGone = null;
		this.dirNode = null;
	}

	public FileNode(Long id, String fileName, Byte[] oneKbHash, 
			Byte[] fullHash, String fileSize, Boolean isGone,
			DirNode dirNode) {
		this.id = id;
		this.fileName = fileName;
		this.oneKbHash = oneKbHash;
		this.fullHash = fullHash;
		this.fileSize = fileSize;
		this.isGone = isGone;
		this.dirNode = dirNode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() { 
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Byte[] getOneKbHash() {
		return oneKbHash;
	}

	public void setOneKbHash(Byte[] oneKbHash) {
		this.oneKbHash = oneKbHash;
	}

	public Byte[] getFullHash() {
		return fullHash;
	}

	public void setFullHash(Byte[] fullHash) {
		this.fullHash = fullHash;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public Boolean getIsGone() {
		return isGone;
	}

	public void setIsGone(Boolean isGone) {
		this.isGone = isGone;
	}

	public DirNode getDirNode() {
		return dirNode;
	}

	public void setDirNode(DirNode dirNode) {
		this.dirNode = dirNode;
	}
}
