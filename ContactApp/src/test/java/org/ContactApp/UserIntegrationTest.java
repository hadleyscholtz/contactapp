package org.ContactApp;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.text.MessageFormat;

import org.ContactApp.dto.MockData;
import org.contactapp.dto.TokenDto;
import org.contactapp.dto.Users;
import org.contactapp.dto.UsersDto;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserIntegrationTest extends AbstractIntegrationTests {
	
	@Test
	public void _1_testUserAdd() throws Exception {
		UsersDto requestDto = MockData.userRequest();
		
		RequestEntity<UsersDto> requestEntity = new RequestEntity<UsersDto>(
				requestDto, 
				HttpMethod.PUT, 
				new URI(USERS_PATH));
				
		ResponseEntity<String> responseEntity = this.restTemplate.exchange(requestEntity, String.class);
		
		assertThat(responseEntity).isNotNull();
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(responseEntity.getBody()).isEqualToIgnoringCase("User added successfully.");
	}
	
	@Test
	public void _2_testUserLogin() throws Exception {
		
		//Step 1
		UsersDto requestDto = MockData.userRequest();
		
		RequestEntity<UsersDto> requestEntity = new RequestEntity<UsersDto>(
				requestDto, 
				HttpMethod.POST, 
				new URI(LOGIN));
				
		ResponseEntity<TokenDto> responseEntity = this.restTemplate.exchange(requestEntity, TokenDto.class);
		
		assertThat(responseEntity).isNotNull();
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		assertThat(responseEntity.getBody().getToken()).isNotNull();
	}
	
	@Test
	public void _3_testUserFind() throws Exception {
		
		//Step 1: Login to retrieve token
		UsersDto requestDto = MockData.userRequest();
		
		RequestEntity<UsersDto> requestEntity = new RequestEntity<UsersDto>(
				requestDto, 
				HttpMethod.POST, 
				new URI(LOGIN));
				
		ResponseEntity<TokenDto> responseEntity = this.restTemplate.exchange(requestEntity, TokenDto.class);
		
		assertThat(responseEntity).isNotNull();
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		assertThat(responseEntity.getBody().getToken()).isNotNull();
		
		//Step 2: Use token to find user
		HttpHeaders headers = new HttpHeaders();
		headers.add(HEADER_USER_TOKEN, responseEntity.getBody().getToken());
		RequestEntity<UsersDto> findUserRequestEntity = new RequestEntity<UsersDto>(
				null, 
				headers,
				HttpMethod.GET, 
				new URI(USERS_PATH));
				
		ResponseEntity<Users> findUserResponseEntity = this.restTemplate.exchange(findUserRequestEntity, Users.class);
		
		assertThat(findUserResponseEntity).isNotNull();
		assertThat(findUserResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(findUserResponseEntity.getBody()).isNotNull();
		assertThat(findUserResponseEntity.getBody().getUserList()).isNotEmpty();
	}
	
	@Test
	public void _4_testUserLogout() throws Exception {
		
		//Step 1: Login to retrieve token
		UsersDto requestDto = MockData.userRequest();
		
		RequestEntity<UsersDto> requestEntity = new RequestEntity<UsersDto>(
				requestDto, 
				HttpMethod.POST, 
				new URI(LOGIN));
				
		ResponseEntity<TokenDto> responseEntity = this.restTemplate.exchange(requestEntity, TokenDto.class);
		
		assertThat(responseEntity).isNotNull();
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isNotNull();
		assertThat(responseEntity.getBody().getToken()).isNotNull();
		
		//Step 2: Use token to log out
		RequestEntity<UsersDto> logoutUserRequestEntity = new RequestEntity<UsersDto>(
				HttpMethod.GET, 
				new URI(MessageFormat.format(LOGOUT, responseEntity.getBody().getId())));
				
		ResponseEntity<TokenDto> logoutUserResponseEntity = this.restTemplate.exchange(logoutUserRequestEntity, TokenDto.class);
		
		assertThat(logoutUserResponseEntity).isNotNull();
		assertThat(logoutUserResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(logoutUserResponseEntity.getBody()).isNotNull();
		assertThat(logoutUserResponseEntity.getBody().getToken()).isNotNull();
	}
}