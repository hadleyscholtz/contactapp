package org.gk.contactapp.facade;

import org.gk.contactapp.entity.Token;

public interface TokenFacade {
	
	public Token addNewToken(Token token);
	
	public Token findByToken(String token);
	
	public Token findById(Long id);

}
