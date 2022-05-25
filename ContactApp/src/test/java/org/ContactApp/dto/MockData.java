package org.ContactApp.dto;

import org.contactapp.dto.UsersDto;

public class MockData {
	
	public static UsersDto userRequest() {
		UsersDto usersDto = new UsersDto();
		
		usersDto.setUserName("blah_blah_user");
		usersDto.setPassword("54d6cab4700349f7a7722d1bcd6d84bd");
		usersDto.setPhone("+123456789");
		
		return usersDto;
	}
}