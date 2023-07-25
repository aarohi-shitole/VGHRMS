package com.techvg.hrms.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.techvg.hrms.web.rest.vm.ErrorResponse;

@ControllerAdvice
public class BusinessRuleExceptionHandler {

	@ExceptionHandler(BusinessRuleException.class)
	public ResponseEntity<ErrorResponse> handleBusinessRuleException(BusinessRuleException ex) {
		// Create a custom error response
		String errorMessage = "An error occurred due to: " + ex.getMessage();
		ErrorResponse errorResponse = new ErrorResponse(errorMessage, ex.getDetails(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR");

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

}
