package com.techvg.hrms.web.rest.errors;

public class CustomException extends RuntimeException {

    public CustomException(String message, String entityName, String errorKey) {
        super(message);
    }
}
