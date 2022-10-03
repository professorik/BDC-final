package com.example.bdc.network.topic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

    /**
     * Saves topic if it doesn't exist in DB.
     *
     * @param topic Topic
     * @return Topic - new topic if it wasn't in DB or @param otherwise.
     * @see Topic
     */
    default Topic saveIfNotExist(Topic topic) {
        return findByName(topic.getName()).orElseGet(() -> save(topic));
    }

    /**
     * Saves list of topics.
     * For each topic saves whether it doesn't exist.
     * This method calls {@link #saveIfNotExist(Topic)} for each topic.
     *
     * @param topics List<Topic>
     * @return List<Topic> - topics list.
     * @see Topic
     */
    default List<Topic> saveIfNotExist(List<Topic> topics) {
        return topics.stream().map(this::saveIfNotExist).toList();
    }

    /**
     * Gets topic by name.
     *
     * @param name String
     * @return Optional<Topic> - a topic with such name or Optional.empty() otherwise.
     * @see Topic
     */
    Optional<Topic> findByName(String name);
}
