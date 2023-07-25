package com.techvg.hrms.web.rest.vm;

public class ErrorResponse {
	
	
	private int status;
	private String error;
	private String message;
    private String details;
    
    public ErrorResponse(String message, String details, int status, String error) {
		super();
		this.message = message;
		this.details = details;
		this.status = status;
		this.error = error;
	}
    
    public ErrorResponse() {
		super();
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
