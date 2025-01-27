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

@RestControllerAdvice
public class ControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleDuplicateException(DuplicateException duplicateException) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        logger.error("DuplicateException captured: Status = {}, Error = {}, Message = {}", httpStatus, httpStatus.getReasonPhrase(), duplicateException.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(duplicateException.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException notFoundException) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        logger.error("DuplicateException captured: Status = {}, Error = {}, Message = {}", httpStatus, httpStatus.getReasonPhrase(), notFoundException.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(notFoundException.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        logger.error("DuplicateException captured: Status = {}, Error = {}, Message = {}", httpStatus, httpStatus.getReasonPhrase(), exception.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Ocorreu um erro inesperado.")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
