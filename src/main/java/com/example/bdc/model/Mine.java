package com.example.bdc.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * This class stores mine for being
 * mapped to JSON.
 */
@JsonPropertyOrder({"x", "y", "level"})
public record Mine(int x, int y, int level) {

}
