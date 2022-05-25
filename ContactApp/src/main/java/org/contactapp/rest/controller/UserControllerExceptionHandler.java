package org.contactapp.rest.controller;

import javax.servlet.http.HttpServletResponse;

import org.contactapp.dto.ErrorDto;
import org.contactapp.enums.ErrorCodes;
import org.contactapp.exceptions.InvalidCredentialsException;
import org.contactapp.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerExceptionHandler {
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorDto> handleInvalidCredentials(
			HttpServletResponse httpResp) {
		
		httpResp.setStatus(HttpStatus.BAD_REQUEST.value());
		
		ErrorDto error = new ErrorDto();
		error.setErrorCode(ErrorCodes.INVALID_CREDENTIALS.getCode());
		error.setErrorMessage(ErrorCodes.INVALID_CREDENTIALS.getDescription());
		
		return new ResponseEntity<ErrorDto>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorDto> handleUserAlreadyExists(
			HttpServletResponse httpResp) {
		
		httpResp.setStatus(HttpStatus.BAD_REQUEST.value());
		
		ErrorDto error = new ErrorDto();
		error.setErrorCode(ErrorCodes.USER_ALREADY_EXISTS.getCode());
		error.setErrorMessage(ErrorCodes.USER_ALREADY_EXISTS.getDescription());
		
		return new ResponseEntity<ErrorDto>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDto> handleException(
			Exception e,
			HttpServletResponse httpResp
			) {
		
		httpResp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		ErrorDto error = new ErrorDto();
		error.setErrorCode(ErrorCodes.UNEXPECTED_ERROR.getCode());
		error.setErrorMessage(ErrorCodes.UNEXPECTED_ERROR.getDescription());
		
		return new ResponseEntity<ErrorDto>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
}