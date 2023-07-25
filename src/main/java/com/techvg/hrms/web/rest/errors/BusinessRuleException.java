package com.techvg.hrms.web.rest.errors;

public class BusinessRuleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String details;

	public BusinessRuleException(String message, String details) {
		super(message);
		this.details = details;
	}

	public String getDetails() {
		return details;
	}

}
