package org.contactapp.facade;

import org.contactapp.entity.Token;

public interface TokenFacade {
	
	public Token addNewToken(Token token);
	
	public Token findByToken(String token);
	
	public Token findById(Long id);

}
