package com.example.bdc.network.user;

import com.example.bdc.network.exception.UserAlreadyExistsException;
import com.example.bdc.network.topic.Topic;
import com.example.bdc.network.topic.TopicRepository;
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
    public UserDto createUpdateUsers(CreateUserDto userDto) {
        if (userRepository.findByName(userDto.getName()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        var user = User.fromDto(userDto);
        var topics = topicRepository.saveIfNotExist(userDto.getTopics().stream().map(Topic::fromName).toList());
        user.getTopics().addAll(topics);
        return UserDto.fromEntity(userRepository.save(user));
    }

    @Transactional
    public void addTrustConnection(String name, Map<String, Integer> connections) {
        var benefactor = userRepository.findByName(name).orElseThrow();
        connections.forEach((toName, level) -> {
            if (toName.equals(name)) return;
            var beneficiary = userRepository.findByName(toName);
            //FIXME: throw exception if user doesn't exist
            beneficiary.ifPresent(user -> connectionRepository.save(benefactor, user, level));
        });
        System.out.println(benefactor);
    }
}
