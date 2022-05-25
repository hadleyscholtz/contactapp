package org.contactapp.facade;

import java.util.List;

import org.contactapp.entity.Users;

public interface UsersFacade {
	
	public Users addNewUser(Users user);
	
	public Users findUserByUserName(String userName);
	
	public List<Users> findAlUsers();

}