package org.gk.contactapp.manager;

import org.gk.contactapp.dto.TokenDto;
import org.gk.contactapp.dto.Users;
import org.gk.contactapp.dto.UsersDto;

public interface UsersManager {
	
	public TokenDto login(UsersDto userDto);
	
	public TokenDto logout(Long tokenId);
	
	public Boolean addNewUsers(UsersDto usersDto);
	
	public Users getUsers(String userToken);

}