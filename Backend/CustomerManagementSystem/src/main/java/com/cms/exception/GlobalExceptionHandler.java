package com.cms.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> handleGlobalExceptions(Exception se,WebRequest wr){
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		myErrorDetails.setDescription(wr.getDescription(false));
		myErrorDetails.setMesseage(se.getMessage());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(cmsException.class)
	public ResponseEntity<MyErrorDetails> handleCmsException(cmsException se,WebRequest wr){
		
		MyErrorDetails myErrorDetails = new MyErrorDetails();
		
		myErrorDetails.setLocalDateTime(LocalDateTime.now());
		myErrorDetails.setDescription(wr.getDescription(false));
		myErrorDetails.setMesseage(se.getMessage());
		
		return new ResponseEntity<MyErrorDetails>(myErrorDetails,HttpStatus.BAD_REQUEST);
	}

}
