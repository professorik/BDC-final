package com.example.bdc.network.exception;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@Data
public class ValidationException extends BadRequestException{

    private String error;

    public ValidationException() {
        super("field validation has failed");
    }
}
