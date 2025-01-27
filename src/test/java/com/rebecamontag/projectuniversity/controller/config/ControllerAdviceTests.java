package com.rebecamontag.projectuniversity.controller.config;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.ErrorResponse;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ControllerAdviceTests {

    private final ControllerAdvice controllerAdvice = new ControllerAdvice();

    @Test
    void testHandleDuplicateException() {
        DuplicateException duplicateException = new DuplicateException("Item already exists.");

        ResponseEntity<ErrorResponse> response = controllerAdvice.handleDuplicateException(duplicateException);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(HttpStatus.CONFLICT.value(), errorResponse.status());
        assertEquals("Item already exists.", errorResponse.message());
    }

    @Test
    void testHandleNotFoundException() {
        NotFoundException notFoundException = new NotFoundException("Resource not found.");

        ResponseEntity<ErrorResponse> response = controllerAdvice.handleNotFoundException(notFoundException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals("Resource not found.", errorResponse.message());
    }

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Unexpected error.");

        ResponseEntity<ErrorResponse> response = controllerAdvice.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Unexpected error.", response.getBody().message());
    }
}
