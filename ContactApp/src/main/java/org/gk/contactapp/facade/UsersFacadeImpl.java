package org.gk.contactapp.facade;

import java.util.List;

import org.gk.contactapp.entity.Users;
import org.gk.contactapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UsersFacadeImpl implements UsersFacade {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Users addNewUser(Users user) {
		return userRepo.saveAndFlush(user);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Users findUserByUserName(String userName) {
		return userRepo.findByUserName(userName);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<Users> findAlUsers() {
		return userRepo.findAll();
	}
}