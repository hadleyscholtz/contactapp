package org.ContactApp;

import org.contactapp.ContactApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = ContactApplication.class)
public abstract class AbstractIntegrationTests {
	
	@Autowired
	protected TestRestTemplate restTemplate;
	protected ObjectMapper objectMapper = new ObjectMapper();
	
	protected static final String USERS_PATH = "/api/users";
	protected static final String LOGIN = "/api/login";
	protected static final String LOGOUT = "/api/logout/{0}";
	protected static final String HEADER_USER_TOKEN = "User-Token";
}