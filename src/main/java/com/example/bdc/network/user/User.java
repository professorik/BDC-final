package com.example.bdc.network.user;

import com.example.bdc.network.topic.Topic;
import com.example.bdc.network.trust_connection.TrustConnection;
import com.example.bdc.network.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "users")
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name="name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_topic",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    @Builder.Default
    private Set<Topic> topics = new HashSet<>();

    @OneToMany(mappedBy = "from")
    @Builder.Default
    private Set<TrustConnection> connections = new HashSet<>();

    public static User fromDto(UserDto user) {
        return User.builder()
                .name(user.getName())
                .build();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("\nid=").append(id);
        sb.append("\nname='").append(name).append('\'');
        sb.append("\ntopics=").append(topics.stream().map(Topic::getName).toList());
        sb.append("\nconnections:\n");
        for (TrustConnection con: connections) {
            sb.append("[user_id: ").
                    append(con.getToName()).
                    append(", ").
                    append("level: ").
                    append(con.getLevel()).
                    append("]\n");
        }
        sb.append('}');
        return sb.toString();
    }
}
