package domain;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer _id;
	private String _username;
	private String _firstName;
	private String _lastName;
	private String _emailAddress;
	private Long _phoneNumber;

	public User() {
	}

	public User(Integer iId, String iUsername, String iFirstName, String iLastName, String iEmailAddress, Long iPhoneNumber) {
		_id = iId;
		_username = iUsername;
		_firstName = iFirstName;
		_lastName = iLastName;
		_emailAddress = iEmailAddress;
		_phoneNumber = iPhoneNumber;
	}

	public Integer getId() {
		return _id;
	}

	public void setId(Integer iId) {
		_id = iId;
	}

	public String getUsername() {
		return _username;
	}

	public void setUsername(String iUsername) {
		_username = iUsername;
	}

	public String getFirstName() {
		return _firstName;
	}

	public void setFirstName(String iFirstName) {
		_firstName = iFirstName;
	}

	public String getLastName() {
		return _lastName;
	}

	public void setLastName(String iLastName) {
		_lastName = iLastName;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public void setEmailAddress(String iEmailAddress) {
		_emailAddress = iEmailAddress;
	}

	public Long getPhoneNumber() {
		return _phoneNumber;
	}

	public void setPhoneNumber(Long iPhoneNumber) {
		_phoneNumber = iPhoneNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
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
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [_id=" + _id + ", _username=" + _username + ", _firstName=" + _firstName + ", _lastName=" + _lastName + ", _emailAddress=" + _emailAddress + ", _phoneNumber="
				+ _phoneNumber + "]";
	}

}
