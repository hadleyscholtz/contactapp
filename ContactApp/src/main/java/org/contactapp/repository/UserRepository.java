package org.contactapp.repository;

import org.contactapp.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	
	public Users findByUserName(String userName);

}