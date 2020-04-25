package com.sschakraborty.platform.kjudge.shared.model;

import javax.persistence.*;
import java.util.Map;

/*
 * Memory limit is in kilobytes (kB)
 */
@Entity
@Table(name = "MEMORY_CONSTRAINT")
public class MemoryConstraint {
	@Id
	@SequenceGenerator(
		name = "MEMORY_CONSTRAINT_SEQ_GEN",
		sequenceName = "MEMORY_CONSTRAINT_SEQ_GEN",
		allocationSize = 1
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "MEMORY_CONSTRAINT_SEQ_GEN"
	)
	@Column(
		name = "ID"
	)
	private long id;

	@Lob
	@Column(
		name = "DESCRIPTION"
	)
	private String description;

	@CollectionTable(
		name = "MEMORY_CONSTRAINT_VALUES"
	)
	@ElementCollection(
		fetch = FetchType.EAGER
	)
	@MapKeyEnumerated(EnumType.STRING)
	private Map<Language, Integer> memoryConstraints;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<Language, Integer> getMemoryConstraints() {
		return memoryConstraints;
	}

	public void setMemoryConstraints(Map<Language, Integer> memoryConstraints) {
		this.memoryConstraints = memoryConstraints;
	}
}