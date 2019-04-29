package org.gk.contactapp.manager;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.gk.contactapp.dto.TokenDto;
import org.gk.contactapp.dto.UsersDto;
import org.gk.contactapp.entity.Token;
import org.gk.contactapp.entity.Users;
import org.gk.contactapp.enums.TokenStatus;
import org.gk.contactapp.exceptions.InvalidCredentialsException;
import org.gk.contactapp.exceptions.UserAlreadyExistsException;
import org.gk.contactapp.facade.TokenFacade;
import org.gk.contactapp.facade.UsersFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UsersManagerImpl implements UsersManager {
	
	@Value("${user.token.expiry_sec:180}")
	private Long userTokenExpirySeconds;
	
	private final Logger log = LoggerFactory.getLogger(UsersManagerImpl.class);
	
	@Autowired
	private UsersFacade usersFacade;
	
	@Autowired
	private TokenFacade tokenFacade;

	@Override
	@Transactional
	public TokenDto login(UsersDto userDto) {
		
		TokenDto tokenDto = null;
		
		log.debug("Finding user with username = [" + userDto.getUserName() + "].");
		
		Users user = usersFacade.findUserByUserName(userDto.getUserName());
		
		log.debug("User retrieved with username = [" + userDto.getUserName() + "]");
		
		if(user != null) {
			//Get current password
			String currentPassword = new String(user.getPassword());
			
			if(currentPassword.equals(userDto.getPassword())) {
				
				log.debug("User authenticated. Creating session token.");
				
				//Password match. Generate a session token
				SecureRandom random = new SecureRandom();
				byte bytes[] = new byte[20];
				random.nextBytes(bytes);
				
				String base64Token = Base64.getEncoder().encodeToString(bytes);
				
				Token token = new Token();
				token.setSessionToken(base64Token);
				token.setUser(user);
				
				Token savedToken = tokenFacade.addNewToken(token);
				
				if(savedToken != null) {
					
					Function<Token, TokenDto> mapEntityToDto = t -> {
						
						TokenDto dto = new TokenDto();
						
						dto.setId(t.getId());
						dto.setToken(t.getSessionToken());
						
						return dto;
					};
					
					tokenDto = mapEntityToDto.apply(savedToken);
					
					log.debug("Session token created.");
				}
			} else {
				throw new InvalidCredentialsException();
			}
		} else {
			throw new InvalidCredentialsException();
		}
		
		return tokenDto;
	} 
	
	@Override
	@Transactional
	public TokenDto logout(Long tokenId) {
		
		TokenDto response = null;
		
		log.debug("Finding token by id.");
		
		Token token = tokenFacade.findById(tokenId);
		
		if(token != null) {
			
			log.debug("Token retrieved.");
			
			response = new TokenDto();
			
			response.setToken(token.getSessionToken());
			
			token.setStatus(TokenStatus.EXPIRED);
			
			tokenFacade.addNewToken(token);
		}
		
		return response;
	}
	
	@Override
	@Transactional
	public Boolean addNewUsers(UsersDto usersDto) {
		
		//First check if user does not exist already
		
		Users user = usersFacade.findUserByUserName(usersDto.getUserName());
		
		if(user != null) {
			throw new UserAlreadyExistsException();
		}
		
		Function<UsersDto, Users> mapToEntity = u -> {
			
			Users newUser = new Users();
			newUser.setUserName(u.getUserName());
			newUser.setPhone(u.getPhone());
			newUser.setPassword(u.getPassword().getBytes());
			
			return newUser;
		};
		
		Users addedUser = usersFacade.addNewUser(mapToEntity.apply(usersDto));
		
		if(addedUser.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public org.gk.contactapp.dto.Users getUsers(String userToken) {
		
		final Token token = tokenFacade.findByToken(userToken);
		
		Boolean tokenValid = false;
		
		if(token != null) {
			//Check that token is still
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime tokenDate = LocalDateTime.ofInstant(token.getLastUpdated().toInstant(), ZoneId.systemDefault());
			
			Duration duration = Duration.between(tokenDate, now);
			
			//Token is no longer valid
			if(duration.getSeconds() >= userTokenExpirySeconds) {
				token.setStatus(TokenStatus.EXPIRED);
			} else {
				tokenValid = true;
			}
		}
		
		final Boolean functionTokenValid = tokenValid;
		
		org.gk.contactapp.dto.Users response = new org.gk.contactapp.dto.Users();
		
		List<Users> users = usersFacade.findAlUsers();
		
		if(users != null) {
			
			Function<Users, UsersDto> mapEntityToDto = u -> {
				UsersDto user = new UsersDto();
				
				user.setUserId(u.getId());
				user.setPhone(u.getPhone());
				
				if(functionTokenValid) {
					
					List<Token> tokens = u.getUserTokens();
					
					if(tokens != null) {
						user.setValidSession(false);
						
						for(Token t : tokens) {
							if(TokenStatus.VALID == t.getStatus()) {
								user.setValidSession(true);
								break;
							}
						}
					}
				}
				
				return user;
			};
			
			List<UsersDto> userList = users.stream().map(mapEntityToDto).collect(Collectors.toList());
			
			response.setUserList(userList);
		}
		
		//Finally, update token last updated
		if(functionTokenValid) {
			token.setLastUpdated(new Date());
		}
		
		if(token != null) {
			
			tokenFacade.addNewToken(token);
		}
		
		
		return response;
	}
}