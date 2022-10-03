package com.example.bdc.network.exception;

import com.example.bdc.network.exception.entities.BadRequestException;
import com.example.bdc.network.exception.entities.InvalidUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * This class implements the exception controller, marked by {@code @ControllerAdvice, @RestController}.
 * It also has the logger to debug.
 */
@Slf4j
@ControllerAdvice
@RestController
public class ExceptionController {

    /**
     * Constraint violation exception handler
     *
     * @param ex ConstraintViolationException
     * @return Map<String, String> - error head and its joined descriptions
     * from set of ConstraintViolation
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolation(ConstraintViolationException ex) {
        var errors = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).toList();
        log.debug("errors", errors);
        return Map.of("error", String.join("\n", errors));
    }

    /**
     * Bad Request exception handler
     *
     * @param e Error
     * @return Map<String, String> - error head and its description
     */
    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> badRequestHandler(Exception e) {
        return Map.of("error", e.getMessage());
    }

    /**
     * Invalid user exceptions handler
     *
     * @param e Exception
     * @return Map<String, String> - error head and its description
     */
    @ExceptionHandler(InvalidUserException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    Map<String, String> invalidUserHandler(Exception e) {
        return Map.of("error", e.getMessage());
    }
}
