package com.rebecamontag.projectuniversity.controller.config;

import com.rebecamontag.projectuniversity.exception.DuplicateException;
import com.rebecamontag.projectuniversity.exception.ErrorResponse;
import com.rebecamontag.projectuniversity.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ControllerAdviceTests {

    private final ControllerAdvice controllerAdvice = new ControllerAdvice();

    @Test
    void testHandleDuplicateException() {
        DuplicateException duplicateException = new DuplicateException("Item already exists.");

        ResponseEntity<ErrorResponse> response = controllerAdvice.handleDuplicateException(duplicateException);

        ErrorResponse errorResponse = response.getBody();
        assertNotNull(errorResponse);
        assertEquals(HttpStatus.CONFLICT.value(), errorResponse.getStatus());
        assertEquals("Conflict", errorResponse.getError());
        assertEquals("Item already exists.", errorResponse.getMessage());
    }

    @Test
    void testHandleNotFoundException() {
        NotFoundException notFoundException = new NotFoundException("Resource not found.");

        ResponseEntity<ErrorResponse> response = controllerAdvice.handleNotFoundException(notFoundException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Not Found", response.getBody().getError());
        assertEquals("Resource not found..", response.getBody().getMessage());
    }

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Unexpected error.");

        ResponseEntity<ErrorResponse> response = controllerAdvice.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Internal Server Error", response.getBody().getError());
        assertEquals("Unexpected error.", response.getBody().getMessage());
    }
}
