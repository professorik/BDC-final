package com.example.bdc.network.topic;

import com.example.bdc.network.user.dto.CreateUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "topic")
public class Topic {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    @Column
    private String name;
}
