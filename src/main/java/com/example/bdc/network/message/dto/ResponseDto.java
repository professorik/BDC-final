package com.example.bdc.network.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseDto {

    private final String from;

    private final List<String> path;
}
