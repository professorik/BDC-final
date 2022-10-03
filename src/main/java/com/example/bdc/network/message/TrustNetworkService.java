package com.example.bdc.network.message;

import com.example.bdc.network.exception.entities.UserDoesNotExistException;
import com.example.bdc.network.message.dto.MessageDto;
import com.example.bdc.network.message.dto.PathResponseDto;
import com.example.bdc.network.topic.Topic;
import com.example.bdc.network.trust_connection.TrustConnection;
import com.example.bdc.network.user.User;
import com.example.bdc.network.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class TrustNetworkService {

    @Autowired
    private UserRepository userRepository;

    public Map<String, Set<String>> addMessage(MessageDto message, boolean bonus) {
        var from = userRepository.findByName(message.getPersonId()).orElseThrow(UserDoesNotExistException::new);
        Map<String, Set<String>> result = new HashMap<>();
        dfs(from, from, message.getLevel(), message.getTopics(), result, new HashSet<>(), bonus);
        return result;
    }

    public PathResponseDto findPath(MessageDto message) {
        var from = userRepository.findByName(message.getPersonId()).orElseThrow(UserDoesNotExistException::new);
        var path = bfs(message.getLevel(), message.getTopics(), from).orElseThrow();
        return new PathResponseDto(message.getPersonId(), path);
    }


    private void dfs(User current, User source, final Integer lvl,
                     final Set<String> topics, Map<String, Set<String>> res,
                     Set<String> visited, final boolean bonus) {

        visited.add(current.getName());
        if (!res.containsKey(source.getName()))
            res.put(source.getName(), new HashSet<>());

        for (TrustConnection con : current.getConnections()) {
            var tmp = con.getTargetUser();
            if (visited.contains(tmp.getName())) continue;
            if (con.getLevel() >= lvl) {
                if (tmp.getTopics().stream().map(Topic::getName).toList().containsAll(topics)) {
                    res.get(source.getName()).add(tmp.getName());
                    dfs(tmp, tmp, lvl, topics, res, visited, bonus);
                } else if (bonus)
                    dfs(tmp, source, lvl, topics, res, visited,true);
            }
            visited.add(tmp.getName());
        }
        if (current.equals(source) && res.get(source.getName()).isEmpty()) {
            res.remove(source.getName());
        }
    }

    private Optional<List<String>> bfs(final Integer trustLvl, final Set<String> topics, User start) {
        Queue<Stack<User>> queue = new LinkedList<>();
        Stack<User> startPath = new Stack<>();
        startPath.push(start);
        queue.add(startPath);
        while (!queue.isEmpty()) {
            var path = queue.poll();
            var current = path.peek();
            if (!current.equals(start) && current.getTopics().stream().map(Topic::getName).toList().containsAll(topics)) {
                path.remove(0);
                return Optional.of(path.stream().map(User::getName).toList());
            }
            for (TrustConnection con : current.getConnections()) {
                if (con.getLevel() >= trustLvl && !path.contains(con.getTargetUser())) {
                    Stack<User> newPath = new Stack<>();
                    newPath.addAll(path);
                    newPath.push(con.getTargetUser());
                    queue.add(newPath);
                }
            }
        }
        return Optional.empty();
    }
}
