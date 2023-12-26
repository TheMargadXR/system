package Scheme;

public class LoginResponse {
	private boolean success;
	private String message;
	private String token;
	
	public LoginResponse() {
		this(false, "", "");
	}
	
	public LoginResponse(String token) {
		this(true , "", token);
	}

	public LoginResponse(boolean success, String message, String token) {
		this.success = success;
		this.message = message;
		this.token = token;
 	}
	
	public LoginResponse(boolean success, String message) {
		this.success = success;
		this.message = message;
		this.token = "";
 	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	

}
