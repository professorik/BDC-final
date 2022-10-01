package com.example.bdc.network.topic;

import com.example.bdc.network.topic.dto.TopicDto;
import com.example.bdc.network.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    @Getter private Integer id;
    @Column
    @Getter private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "topics", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<User> users = new HashSet<>();


    public static Topic fromDto(TopicDto topic) {
        return Topic.builder()
                .id(topic.getId())
                .name(topic.getName())
                .build();
    }

    public static Topic fromName(String name) {
        return Topic.builder()
                .name(name)
                .build();
    }
}
