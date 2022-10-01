package com.example.bdc.network.user.dto;

import com.example.bdc.network.topic.Topic;
import com.example.bdc.network.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@JsonPropertyOrder({"id", "topics"})
public class UserDto {

    @JsonProperty("id")
    private final String name;

    private final List<String> topics;

    public static UserDto fromEntity(User user) {
        return UserDto
                .builder()
                .name(user.getName())
                .topics(user.getTopics().stream().map(Topic::getName).toList())
                .build();
    }
}
