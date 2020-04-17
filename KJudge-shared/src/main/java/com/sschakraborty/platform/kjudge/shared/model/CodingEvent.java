package com.sschakraborty.platform.kjudge.shared.model;

import java.time.LocalDateTime;

public class CodingEvent {
	private CodingEventType eventType;
	private ParticipationType participationType;
	private String eventName;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

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
}