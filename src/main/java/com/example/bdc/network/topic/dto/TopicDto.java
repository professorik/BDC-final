package com.example.bdc.network.topic.dto;

import com.example.bdc.network.topic.Topic;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopicDto {

    private Integer id;

    private String name;

    public static TopicDto fromEntity(Topic topic) {
        return TopicDto
                .builder()
                .id(topic.getId())
                .name(topic.getName())
                .build();
    }
}
