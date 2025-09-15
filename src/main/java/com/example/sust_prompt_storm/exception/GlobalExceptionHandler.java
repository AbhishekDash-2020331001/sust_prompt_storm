package com.example.sust_prompt_storm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.example.sust_prompt_storm.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        // Check if this is an update request (VoterUpdateRequest) or registration request
        String requestPath = ex.getParameter().getMethod().getDeclaringClass().getSimpleName();
        String methodName = ex.getParameter().getMethod().getName();
        
        if (methodName.equals("updateVoter")) {
            // For update requests, return 422 with specific age validation message
            String message = "invalid age: " + ex.getBindingResult().getFieldError("age").getRejectedValue() + ", must be 18 or older";
            ErrorResponse errorResponse = new ErrorResponse(message);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
        } else {
            // For registration requests, return 409
            String message = "Age must be 18 or older";
            ErrorResponse errorResponse = new ErrorResponse(message);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
    }
    
    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex) {
        String message = "Invalid voter ID format";
        ErrorResponse errorResponse = new ErrorResponse(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
