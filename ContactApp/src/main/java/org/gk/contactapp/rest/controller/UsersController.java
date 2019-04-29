package org.gk.contactapp.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gk.contactapp.dto.TokenDto;
import org.gk.contactapp.dto.Users;
import org.gk.contactapp.dto.UsersDto;
import org.gk.contactapp.manager.UsersManager;
import org.jboss.logging.NDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api")
public class UsersController {
	
	private static final String HEADER_USER_TOKEN = "User-Token";
	
	@Autowired
	private UsersManager usersManager;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<TokenDto> login(
			@RequestBody UsersDto user,
			HttpServletRequest httpReq,
			HttpServletResponse httpResp
			) {
		
		TokenDto response = null;
		
		NDC.push("[Users: Login]");
		
		try {
			
			response = usersManager.login(user);
			
		} finally {
			NDC.pop();
		}
		
		return new ResponseEntity<TokenDto>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/logout/{token_id}", method = RequestMethod.GET)
	public ResponseEntity<TokenDto> logout(
			@PathVariable("token_id") Long id,
			HttpServletRequest httpReq,
			HttpServletResponse httpResp
			) {
		
		TokenDto response = null;
		
		NDC.push("[Users: Logout]");
		
		try {
			
			response = usersManager.logout(id);
			
		} finally {
			NDC.pop();
		}
		
		return new ResponseEntity<TokenDto>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public ResponseEntity<String> addNewUser(
		@RequestBody UsersDto user,
		HttpServletRequest httpReq,
		HttpServletResponse httpResp
			){
		
		NDC.push("[Users : Add New User]");
		
		Boolean result = false;
		String response = "";
		
		try {
			result = usersManager.addNewUsers(user);
			
			if(result) {
				response = "User added successfully.";
			} else {
				response = "No user added.";
			}
		} finally {
			NDC.pop();
		}
		
		return new ResponseEntity<String>(response, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<Users> getUser(
			HttpServletRequest httpReq,
			HttpServletResponse httpResp
			){
		
		Users response = null;
		
		NDC.push("[Users : Get Users]");
		
		String userToken = httpReq.getHeader(HEADER_USER_TOKEN);
		
		try {
			
			response = usersManager.getUsers(userToken);
			
		} finally {
			NDC.pop();
		}
		
		return new ResponseEntity<Users>(response, HttpStatus.OK);
	}
}