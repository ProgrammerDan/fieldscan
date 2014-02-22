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
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;

import com.programmerdan.fieldscan.model.NodeProcessorConfig;
import com.programmerdan.fieldscan.model.FieldCanConfig;

/**
 * Configuration bean for a node processor.
 *
 * TODO: Comment appropriately
 *
 * @author Daniel Boston <programmerdan@gmail.com>
 * @since 0.1-SNAPSHOT
 * @version 0.1-SNAPSHOT Feb 17, 2014
 *   Initial version, basic options and tying this configuration
 *   to simple registration based "Processor" notion.
 */
@Entity
@Table(name="processor_config")
public class NodeProcessorConfig implements Serializable{

	/**
	 * Internal serial ID to allow other methods of storage besides
	 * JDBC.
	 */
	private static final long serialVersionUID = 71375247005;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id")
	private Long id;

	@Column(name="name", nullable=false)
	private String name;

	@Column(name="class", nullable=false)
	private String className;

	/**
	 * Free form parameters block. TODO: replace with key/value system, or
	 * json block, or something.
	 */
	@Column(name="params")
	private String params;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="base_config_id", table="base_config", referencedColumnName="id",
			nullable=false);
	private FieldScanConfig baseConfig;

	public NodeProcessorConfig() {
		id = null;
		name = null;
		className = null;
		params = null;
		baseConfig = null;
	}

	public NodeProcessorConfig(Long id, String name, String className,
			String params, FieldScanConfig baseConfig) {
		this.id = id;
		this.name = name;
		this.className = className;
		this.params = params;
		this.baseConfig = baseConfig;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public FieldScanConfig getBaseConfig() {
		return this.baseConfig();
	}

	public void setBaseConfig(FieldScanConfig baseConfig) {
		this.baseConfig = baseConfig;
	}

}
