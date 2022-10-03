package com.example.bdc.network.topic.dto;

import lombok.Builder;
import lombok.Data;

/**
 * This class stores a topic for being
 * mapped from/to JSON args.
 */
@Data
@Builder
public class TopicDto {

    private Integer id;

    private String name;
}
