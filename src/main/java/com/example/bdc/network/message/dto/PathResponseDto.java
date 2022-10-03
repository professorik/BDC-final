package com.example.bdc.network.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * This class stores a path for being
 * mapped to JSON.
 */
@Data
@AllArgsConstructor
public class PathResponseDto {

    private final String from;

    private final List<String> path;
}
