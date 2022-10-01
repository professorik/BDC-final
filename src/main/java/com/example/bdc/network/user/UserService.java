package com.example.bdc.network.user;

import com.example.bdc.network.topic.Topic;
import com.example.bdc.network.topic.TopicRepository;
import com.example.bdc.network.trust_connection.TrustConnection;
import com.example.bdc.network.trust_connection.TrustConnectionRepository;
import com.example.bdc.network.user.dto.CreateUserDto;
import com.example.bdc.network.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TrustConnectionRepository connectionRepository;

    @Transactional
    public Optional<UserDto> createUpdateUsers(CreateUserDto userDto) {
        try {
            var user = userRepository.findByName(userDto.getName()).orElseGet(() -> User.fromDto(userDto));
            var topics = topicRepository.saveIfNotExist(userDto.getTopics().stream().map(Topic::fromName).toList());

            user.getTopics().addAll(topics);
            return Optional.of(UserDto.fromEntity(userRepository.save(user)));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void addTrustConnection(String name, Map<String, Integer> connections) {
        var benefactor = userRepository.findByName(name).orElseThrow();
        connections.forEach((k, v) -> {
            var beneficiary = userRepository.findByName(k);
            beneficiary.ifPresent(user -> connectionRepository.save(benefactor, user, v));
        });
    }
}
