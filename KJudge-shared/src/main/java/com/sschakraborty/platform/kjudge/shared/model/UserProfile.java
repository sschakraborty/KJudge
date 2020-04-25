package com.sschakraborty.platform.kjudge.shared.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USER_PROFILE")
public class UserProfile {
	@Id
	@Column(
		name = "PRINCIPAL",
		length = 25
	)
	private String userPrincipal;

	@Column(
		name = "FIRST_NAME",
		nullable = false,
		length = 25
	)
	private String firstName;

	@Column(
		name = "MIDDLE_NAME",
		length = 25
	)
	private String middleName;

	@Column(
		name = "LAST_NAME",
		nullable = false,
		length = 25
	)
	private String lastName;

	@Column(
		name = "DISPLAY_NAME",
		nullable = false,
		length = 25
	)
	private String displayName;

	@CollectionTable(
		name = "USER_PROFILE_EMAILS"
	)
	@ElementCollection
	private List<String> emails;

	@CollectionTable(
		name = "USER_PROFILE_PH_NUMBERS"
	)
	@ElementCollection
	private List<String> phoneNumbers;

	public String getUserPrincipal() {
		return userPrincipal;
	}

	public void setUserPrincipal(String userPrincipal) {
		this.userPrincipal = userPrincipal;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
}