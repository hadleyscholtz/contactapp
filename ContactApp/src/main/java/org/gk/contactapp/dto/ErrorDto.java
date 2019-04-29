package org.gk.contactapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDto {
	
	@JsonProperty("error_code")
	private String errorCode;
	
	@JsonProperty("error_message")
	private String errorMessage;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}