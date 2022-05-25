package org.contactapp.facade;

import org.contactapp.entity.Token;
import org.contactapp.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TokenFacadeImpl implements TokenFacade {
	
	@Autowired
	private TokenRepository tokenRepo;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Token addNewToken(Token token) {
		return tokenRepo.saveAndFlush(token);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Token findByToken(String token) {
		return tokenRepo.findBySessionToken(token);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Token findById(Long id) {
		return tokenRepo.getOne(id);
	}
}
