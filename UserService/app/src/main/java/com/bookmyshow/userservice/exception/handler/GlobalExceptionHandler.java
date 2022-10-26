package com.bookmyshow.userservice.exception.handler;

import com.bookmyshow.userservice.exception.PermissionNotFoundException;
import com.bookmyshow.userservice.exception.RoleNotFoundException;
import com.bookmyshow.userservice.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RoleNotFoundException.class, PermissionNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<APIError> handleNotFoundException(RuntimeException exception, WebRequest request){
        return new ResponseEntity<>(new APIError(exception.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errorMap = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, fieldError -> {
                    String message = fieldError.getDefaultMessage();
                    if(message!=null) return message;
                    else return "";
                }));

        return new ResponseEntity<>(new APIError(ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI(), errorMap), HttpStatus.BAD_REQUEST);
    }

}
