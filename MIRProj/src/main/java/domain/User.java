package domain;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String username;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private Long phoneNumber;

	public User() {
	}

	public User(Integer iId, String iUsername, String iFirstName, String iLastName, String iEmailAddress, Long iPhoneNumber) {
		id = iId;
		username = iUsername;
		firstName = iFirstName;
		lastName = iLastName;
		emailAddress = iEmailAddress;
		phoneNumber = iPhoneNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer iId) {
		id = iId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String iUsername) {
		username = iUsername;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String iFirstName) {
		firstName = iFirstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String iLastName) {
		lastName = iLastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String iEmailAddress) {
		emailAddress = iEmailAddress;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long iPhoneNumber) {
		phoneNumber = iPhoneNumber;
	}

	@Override
	public String toString() {
		return "User [_id=" + id + ", _username=" + username + ", _firstName=" + firstName + ", _lastName=" + lastName + ", _emailAddress=" + emailAddress + ", _phoneNumber="
				+ phoneNumber + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		return true;
	}

}
