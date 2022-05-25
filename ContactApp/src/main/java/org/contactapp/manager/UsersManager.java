package org.contactapp.manager;

import org.contactapp.dto.TokenDto;
import org.contactapp.dto.Users;
import org.contactapp.dto.UsersDto;

public interface UsersManager {
	
	public TokenDto login(UsersDto userDto);
	
	public TokenDto logout(Long tokenId);
	
	public Boolean addNewUsers(UsersDto usersDto);
	
	public Users getUsers(String userToken);

}