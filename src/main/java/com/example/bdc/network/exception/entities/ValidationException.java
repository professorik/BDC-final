package com.example.bdc.network.exception.entities;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@Data
public class ValidationException extends BadRequestException{

    private String error;
}
