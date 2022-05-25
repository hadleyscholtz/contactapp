package org.contactapp.enums;

public enum ErrorCodes {
	
	INVALID_CREDENTIALS("CA-100", "Invalid username / password. Please try again"),
	USER_ALREADY_EXISTS("CA-101", "User already exists. Please try a different username."),
	UNEXPECTED_ERROR("CA-999", "An unexpected error occurred during program execution");
	
	private String code;
	private String description;
	
	private ErrorCodes(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}