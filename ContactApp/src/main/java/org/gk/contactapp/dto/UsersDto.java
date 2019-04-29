package org.gk.contactapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersDto {
	
	@JsonProperty("user_id")
	private Long userId;
	
	@JsonProperty("user_name")
	private String userName;
	
	@JsonProperty("phone")
	private String phone;
	
	@JsonProperty("password")
	private String password;
	
	@JsonProperty("valid_session")
	private Boolean validSession;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getValidSession() {
		return validSession;
	}

	public void setValidSession(Boolean validSession) {
		this.validSession = validSession;
	}
}