package com.csis.reminder.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.csis.reminder.entity.enumeration.EventType;
import com.csis.reminder.util.ScreenUtil;

/**
 * @author Reminder Group Class responsible for storing Event attributes and
 *         database configuration for Event table
 *
 */
@Entity
@Table
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch=FetchType.EAGER)
	private Course course;

	@Column(nullable = false)
	private String eventName;

	@Column(nullable = false)
	private Date date;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EventType type;

	private String description;

	public String getDateStr() {
		try {
			return new SimpleDateFormat(ScreenUtil.DATE_TIME_FORMAT).format(date);
		} catch (Exception e) {
			return "";
		}
	}

	public Object[] getData() {
		return new Object[] { id, course.getCourseName(), type.toString(), getDateStr(), eventName, description };
	}

	
	@Override
	public String toString() {
		return eventName;
	}
	
	public String DisplayInfo()	{
		String output = "";
		output += "\nID: " +getId()
		+ "\nCourse: " + getCourse()
		+ "\nEvent Name: " + getEventName()
		+ "\nDate: " + getDateStr()
		+ "\nEvent Type: " + getType()
		+ "\nDescription: " + getDescription();
		return output;
	}
	
	// getters and setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

}
