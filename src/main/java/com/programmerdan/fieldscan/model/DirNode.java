package com.programmerdan.fieldscan.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;

/**
 * Basic directory node. Might hold more in here eventually but for now 
 * just a name.
 *
 * TODO: Comment appropriately
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT Feb 16, 2014
 *   Initial version, sets up a simple node for future comparisons.
 */
@Entity
@Table(name="dir_node")
public class DirNode implements BaseNode, Serializable{

	/**
	 * Internal serial ID to allow other methods of storage besides
	 * JDBC.
	 */
	private static final long serialVersionUID = 71375247002;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id")
	public Long id;

	@Column(name="directory_name", nullable=false)
	public String directoryName;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(referencedColumnName="dir_node_id", table="file_node")
	public Set<FileNode> fileNodes;

	public DirNode() {
		this.id = null;
		this.directoryName = null;
		this.fileNodes = null;
	}

	public DirNode(Long id, String directoryName, Set<FileNode> fileNodes) {
		this.id = id;
		this.directoryName = directoryName;
		this.fileNodes = fileNodes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		Long id = id;
	}


	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	
	public Set<FileNode> getFileNodes() {
		return fileNodes;
	}

	public void setFileNodes(Set<FileNode> fileNodes) {
		this.fileNodes = fileNodes;
	}
}
