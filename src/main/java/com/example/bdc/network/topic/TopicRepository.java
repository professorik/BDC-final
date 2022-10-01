package com.example.bdc.network.topic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

    default Topic saveIfNotExist(Topic topic) {
        return findByName(topic.getName()).orElseGet(() -> save(topic));
    }

    default List<Topic> saveIfNotExist(List<Topic> topics) {
        return topics.stream().map(this::saveIfNotExist).toList();
    }

    Optional<Topic> findByName(String name);
}
