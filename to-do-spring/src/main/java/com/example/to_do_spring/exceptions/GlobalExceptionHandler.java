package com.example.to_do_spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ResponseError> buildResponse(Exception ex, HttpStatus status) {
        ResponseError error = ResponseError.builder()
                .status(status.value())
                .error(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseError> handleBusiness(BusinessException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseError> handleNotFound(NotFoundException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseError> handleBadRequest(BadRequestException ex) {
        return buildResponse(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseError> handleUnauthorized(UnauthorizedException ex) {
        return buildResponse(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleGeneral(Exception ex) {
        return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
