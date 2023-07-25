package com.techvg.hrms.web.rest;

import com.techvg.hrms.web.rest.errors.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), null, null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    //@ExceptionHandler(CustomException.class)
    //public ResponseEntity<Map<String, String>> handleMyCustomException(CustomException ex) {
    //  Map<String, String> error = new HashMap<>();
    //  error.put("error", ex.getMessage());
    //  error.put("entityName", "MyEntity"); // replace with the name of your entity
    //  return ResponseEntity.badRequest().body(error);
    //}

    private static class ErrorResponse {

        private final String message;

        private final String entityName;

        private final String errorKey;

        public ErrorResponse(String message, String errorKey, String entityName) {
            this.message = message;
            this.entityName = entityName;
            this.errorKey = errorKey;
        }

        public String getMessage() {
            return message;
        }

        public String getEntityName() {
            return entityName;
        }

        public String getErrorKey() {
            return errorKey;
        }
    }
}
