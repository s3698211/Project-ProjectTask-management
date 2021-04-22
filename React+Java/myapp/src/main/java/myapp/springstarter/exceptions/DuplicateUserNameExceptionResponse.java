package myapp.springstarter.exceptions;

public class DuplicateUserNameExceptionResponse {
	private String username;

	public DuplicateUserNameExceptionResponse(String username) {
		
		this.username = username;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}
	
	
	
}
