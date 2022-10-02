package com.example.bdc.network.message;

import com.example.bdc.network.message.dto.MessageDto;
import com.example.bdc.network.message.dto.ResponseDto;
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
        var from = userRepository.findByName(message.getPersonId()).orElseThrow();
        Map<String, Set<String>> result = new HashMap<>();
        dfs(from, message.getLevel(), message.getTopics(), result, bonus);
        return result;
    }

    public ResponseDto findPath(MessageDto message) {
        var from = userRepository.findByName(message.getPersonId()).orElseThrow();
        var path = bfs(message.getLevel(), message.getTopics(), from).orElseThrow();
        return new ResponseDto(message.getPersonId(), path);
    }

    private void dfs(User vertex, final Integer lvl, final Set<String> topics, Map<String, Set<String>> res, final boolean bonus) {
        res.put(vertex.getName(), new HashSet<>());

        for (TrustConnection con : vertex.getConnections()) {
            var tmp = con.getTargetUser();
            if (res.containsKey(tmp.getName())) continue;
            if (con.getLevel() >= lvl) {
                if (tmp.getTopics().stream().map(Topic::getName).toList().containsAll(topics)) {
                    res.get(vertex.getName()).add(tmp.getName());
                    if (!bonus)
                        dfs(tmp, lvl, topics, res, false);
                }
                if (bonus)
                    dfs(tmp, lvl, topics, res, true);
            }
        }
        if (res.get(vertex.getName()).isEmpty()) {
            res.remove(vertex.getName());
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
