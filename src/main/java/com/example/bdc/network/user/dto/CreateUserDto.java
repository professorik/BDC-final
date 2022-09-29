package com.example.bdc.network.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserDto {
    private final Integer id;
    private final String name;
}
