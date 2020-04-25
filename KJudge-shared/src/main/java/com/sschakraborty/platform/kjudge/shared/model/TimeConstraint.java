package com.sschakraborty.platform.kjudge.shared.model;

import javax.persistence.*;
import java.util.Map;

/*
 * Time is in millisecond (ms)
 */
@Entity
@Table(name = "TIME_CONSTRAINT")
public class TimeConstraint {
	@Id
	@SequenceGenerator(
		name = "TIME_CONSTRAINT_SEQ_GEN",
		sequenceName = "TIME_CONSTRAINT_SEQ_GEN",
		allocationSize = 1
	)
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "TIME_CONSTRAINT_SEQ_GEN"
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
		name = "TIME_CONSTRAINT_VALUES"
	)
	@ElementCollection(
		fetch = FetchType.EAGER
	)
	@MapKeyEnumerated(EnumType.STRING)
	private Map<Language, Integer> timeConstraints;

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

	public Map<Language, Integer> getTimeConstraints() {
		return timeConstraints;
	}

	public void setTimeConstraints(Map<Language, Integer> timeConstraints) {
		this.timeConstraints = timeConstraints;
	}
}