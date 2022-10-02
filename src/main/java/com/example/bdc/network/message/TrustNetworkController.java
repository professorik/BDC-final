package com.example.bdc.network.message;

import com.example.bdc.network.message.dto.MessageDto;
import com.example.bdc.network.message.dto.ResponseDto;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@Slf4j
@Validated
public class TrustNetworkController {

    @Autowired
    TrustNetworkService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("messages")
    public Map<String, Set<String>> addMessage(@RequestParam Optional<Boolean> bonus, @RequestBody MessageDto message) {
        var isBonus = bonus.orElse(Boolean.FALSE);
        return service.addMessage(message, isBonus);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("path")
    public ResponseDto findPath(@RequestBody MessageDto message) {
        return service.findPath(message);
    }
}
