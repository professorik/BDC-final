package com.example.bdc.controller;

import com.example.bdc.exception.BadRequestException;
import com.example.bdc.exception.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

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
        return Map.of("error", String.join("\n", errors));
    }

    /**
     * Bad Request exception handler
     *
     * @param e Error
     * @return Map<String, String> - error head and its description
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({UnprocessableEntityException.class, BadRequestException.class})
    Map<String, String> badRequestHandler(Exception e) {
        Map<String, String> res = new HashMap<>();
        res.put("error", e.getMessage());
        if (e instanceof UnprocessableEntityException) {
            res.put("details", ((UnprocessableEntityException) e).getDetails());
        }
        return res;
    }
}
