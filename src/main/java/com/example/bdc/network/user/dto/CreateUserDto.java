package com.example.bdc.network.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class CreateUserDto {

    @JsonProperty("id")
    private final String name;

    private final Set<String> topics;
}
