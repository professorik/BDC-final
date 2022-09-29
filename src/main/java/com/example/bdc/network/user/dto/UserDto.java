package com.example.bdc.network.user.dto;

import com.example.bdc.network.user.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    private final Integer id;
    private final String name;

    public static UserDto fromEntity(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
