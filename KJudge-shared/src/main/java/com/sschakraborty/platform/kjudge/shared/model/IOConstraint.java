package com.sschakraborty.platform.kjudge.shared.model;

import javax.persistence.*;
import java.util.Map;

/*
 * IO output size is in kilobytes (kB)
 */
@Entity
@Table(name = "IO_CONSTRAINT")
public class IOConstraint {
	@Id
	@SequenceGenerator(
		name = "IO_CONSTRAINT_SEQ_GEN",
		sequenceName = "IO_CONSTRAINT_SEQ_GEN",
		allocationSize = 1
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "IO_CONSTRAINT_SEQ_GEN"
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
		name = "IO_CONSTRAINT_VALUES"
	)
	@ElementCollection(
		fetch = FetchType.EAGER
	)
	@MapKeyEnumerated(EnumType.STRING)
	private Map<Language, Integer> outputLimits;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Map<Language, Integer> getOutputLimits() {
		return outputLimits;
	}

	public void setOutputLimits(Map<Language, Integer> outputLimits) {
		this.outputLimits = outputLimits;
	}
}