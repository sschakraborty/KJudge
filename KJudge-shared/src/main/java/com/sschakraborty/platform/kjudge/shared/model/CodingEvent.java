package com.sschakraborty.platform.kjudge.shared.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sschakraborty.platform.kjudge.shared.jsonUtility.LocalDateDeserializer;
import com.sschakraborty.platform.kjudge.shared.jsonUtility.LocalDateSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CODING_EVENT")
public class CodingEvent {
	@Id
	@Column(
		name = "EVENT_HANDLE",
		length = 25
	)
	String eventHandle;

	@Column(
		name = "EVENT_TYPE",
		nullable = false
	)
	@Enumerated(EnumType.STRING)
	private CodingEventType eventType;

	@Column(
		name = "PARTICIPATION_TYPE",
		nullable = false
	)
	@Enumerated(EnumType.STRING)
	private ParticipationType participationType;

	@Column(
		name = "EVENT_NAME",
		nullable = false,
		length = 50
	)
	private String eventName;

	@Lob
	@Column(
		name = "DESCRIPTION"
	)
	private String description;

	@Column(
		name = "START_DT",
		nullable = false
	)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDateTime startTime;

	@Column(
		name = "END_DT",
		nullable = false
	)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDateTime endTime;

	@OneToMany(
		fetch = FetchType.LAZY,
		cascade = CascadeType.ALL,
		mappedBy = "codingEvent"
	)
	private List<Problem> problemList;

	public String getEventHandle() {
		return eventHandle;
	}

	public void setEventHandle(String eventHandle) {
		this.eventHandle = eventHandle;
	}

	public CodingEventType getEventType() {
		return eventType;
	}

	public void setEventType(CodingEventType eventType) {
		this.eventType = eventType;
	}

	public ParticipationType getParticipationType() {
		return participationType;
	}

	public void setParticipationType(ParticipationType participationType) {
		this.participationType = participationType;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public List<Problem> getProblemList() {
		return problemList;
	}

	public void setProblemList(List<Problem> problemList) {
		this.problemList = problemList;
	}
}