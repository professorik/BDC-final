package com.example.bdc.network.user;

import com.example.bdc.network.exception.BadRequestException;
import com.example.bdc.network.exception.UserAlreadyExistsException;
import com.example.bdc.network.user.dto.CreateUserDto;
import com.example.bdc.network.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Validated
@RequestMapping("/people")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto createUpdateUser(@RequestBody CreateUserDto user) {
        return userService.createUpdateUsers(user);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{name}/trust_connections")
    public void addTrustConnection(@PathVariable String name, @RequestBody Map<String, Integer> connections) {
        userService.addTrustConnection(name, connections);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> errorHandler(Exception e) {
        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    Map<String, String> badAuthHandler(Exception e) {
        return Map.of("error", e.getMessage());
    }
}
