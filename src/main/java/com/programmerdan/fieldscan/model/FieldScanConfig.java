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

/**
 * Configuration bean for configuring against a database config.
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
@Table(name="base_config")
public class FieldScanConfig implements Serializable{

	/**
	 * Internal serial ID to allow other methods of storage besides
	 * JDBC.
	 */
	private static final long serialVersionUID = 71375247004;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="id")
	private Long id;

	@Column(name="name", unique=true, nullable=false)
	private String configName;

	@Column(name="description")
	private String description;

	/**
	 * Ignored for now. TODO: add support for this.
	 */
	@Column(name="is_parallel_deduplication", nullable=false)
	private Boolean isParallelDeduplication;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="root_processor_id", table="processor_config", 
			referenceColumnName="id", nullable=false)
	private NodeProcessorConfig rootProcessor;

	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(referencedColumnName="base_config_id", table="processor_config")
	private Set<NodeProcessorConfig> registeredProcessors;

	public FieldScanConfig() {
		id = null;
		name = null;
		description = null;
		isParallelDeduplication= null;
		rootProcessor = null;
		registeredProcessors = null;
	}

	public FieldScanConfig(Long id, String name, String description,
			Boolean isParallelDeduplication, NodeProcessorConfig rootProcessor,
			Set<NodeProcessorConfig> registeredProcessors) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.isParallelDeduplication = isParallelDeduplication;
		this.rootProcessor = rootProcessor;
		this.registeredProcessors = registeredProcessors;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsParallelDeduplication() {
		return isParallelDeduplication;
	}

	public void setIsParallelDeduplication(Boolean isParallelDeduplication) {
		this.isParallelDeduplication = isParallelDeduplication;
	}

	public NodeProcessorConfig getRootProcessor() {
		return rootProcessor;
	}

	public void setRootProcessor(NodeProcessorConfig rootProcessor) {
		this.rootProcessor = rootProcessor;
	}

	public Set<NodeProcessorConfig> getRegisteredProcessors() {
		return registeredProcessors;
	}

	public void setRegisteredProcessor(
			Set<NodeProcessorConfig registeredProcessors) {
		this.registeredProcessors = registeredProcessors;
	}
}
