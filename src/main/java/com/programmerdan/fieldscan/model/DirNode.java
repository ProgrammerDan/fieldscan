package com.programmerdan.fieldscan.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Basic directory node. Might hold more in here eventually but for now 
 * just a name.
 *
 * TODO: Comment appropriately
 *
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT Feb 16, 2014
 *   Initial version, sets up a simple node for future comparisons.
 */
@Entity
@Table(name="dir_node")
public class DirNode {

	@Id
	@GeneratedValue(strategy=SEQUENCE)
	@Column(name="id")
	public Long id;

	@Column(name="directory_name", nullable=false)
	public String directoryName;

	public DirNode() {
		this.id = null;
		this.directoryName = null;
	}

	public DirNode(Long id, String directoryName) {
		this.id = id;
		this.directoryName = directoryName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		Long id = id;
	}


	public String getDirectoryName() {
		DirectoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}
}
