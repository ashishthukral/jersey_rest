package domain;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseObject {

	private User user;
	private String success;

	public ResponseObject() {
	}

	public ResponseObject(User iUser, String iSuccess) {
		user = iUser;
		success = iSuccess;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User iUser) {
		user = iUser;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String iSuccess) {
		success = iSuccess;
	}

	@Override
	public String toString() {
		return "ResponseObject [user=" + user + ", success=" + success + "]";
	}

}
