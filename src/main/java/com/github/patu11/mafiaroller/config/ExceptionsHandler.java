package com.github.patu11.mafiaroller.config;

import com.github.patu11.mafiaroller.ErrorBody;
import com.github.patu11.mafiaroller.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    protected ResponseEntity<?> handleIllegalArgumenException(IllegalArgumentException exception, WebRequest request) {
        ErrorBody errorBody = new ErrorBody(400, exception.getMessage());
        return handleExceptionInternal(exception, errorBody, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<?> handleNotFoundException(NotFoundException exception, WebRequest request) {
        ErrorBody errorBody = new ErrorBody(404, exception.getMessage());
        return handleExceptionInternal(exception, errorBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
