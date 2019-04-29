package org.gk.contactapp.repository;

import org.gk.contactapp.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
	
	public Token findBySessionToken(String sessionToken);

}
