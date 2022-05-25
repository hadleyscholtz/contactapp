package org.contactapp.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Users {
	
	@JsonProperty("users")
	private List<UsersDto> userList;

	public List<UsersDto> getUserList() {
		return userList;
	}

	public void setUserList(List<UsersDto> userList) {
		this.userList = userList;
	}
}