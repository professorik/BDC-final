package com.example.bdc.network.user;

import com.example.bdc.network.user.dto.CreateUserDto;
import com.example.bdc.network.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/people")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto createUpdateUser(@RequestBody CreateUserDto user) {
        return userService.createUpdateUsers(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create the user"));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{name}/trust_connections")
    public void addTrustConnection(@PathVariable String name, @RequestBody Map<String, Integer> connections) {
        userService.addTrustConnection(name, connections);
    }
}
