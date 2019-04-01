package com.csis.reminder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.csis.reminder.entity.enumeration.UserType;

/**
 * @author Reminder Group
 * Class responsible for storing user attributes and database configuration for user table
 *
 */
@Entity
@Table
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String firstName;
	private String lastName;
	@Column(unique=true)
	private String email;
	private String password;
	private boolean enabled;
	
	@Enumerated(EnumType.STRING)
	private UserType type;
	
	public Object[] getData() {
		Object[] data = {id,email,firstName,lastName,type.toString(),enabled};
		return data;
	}

	// getters and setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setType(UserType type) {
		this.type = type;
	}
	
	public UserType getType() {
		return type;
	}


}
