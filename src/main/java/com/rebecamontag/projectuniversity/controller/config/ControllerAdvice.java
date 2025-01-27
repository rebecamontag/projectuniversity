package com.rebecamontag.projectuniversity.controller.config;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.ErrorResponse;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateException(DuplicateException duplicateException) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        logger.error("DuplicateException captured: Status = {}, Error = {}, Message = {}", httpStatus, httpStatus.getReasonPhrase(), duplicateException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(Instant.now(),
                httpStatus.value(),
                duplicateException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException notFoundException) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        logger.error("DuplicateException captured: Status = {}, Error = {}, Message = {}", httpStatus, httpStatus.getReasonPhrase(), notFoundException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(Instant.now(),
                httpStatus.value(),
                notFoundException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        logger.error("DuplicateException captured: Status = {}, Error = {}, Message = {}", httpStatus, httpStatus.getReasonPhrase(), exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(Instant.now(),
                httpStatus.value(),
                "Ocorreu um erro inesperado.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
