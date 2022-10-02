package com.example.bdc.network;

import com.example.bdc.network.message.dto.MessageDto;
import com.example.bdc.network.message.dto.ResponseDto;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@Slf4j
@Validated
public class TrustNetworkController {

    @Autowired
    TrustNetworkService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("messages")
    public Map<String, Set<String>> addMessage(@RequestBody MessageDto message) {
        return service.addMessage(message);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("messages/bonus")
    public Map<String, Set<String>> addMessageBonus(@RequestBody MessageDto message) {
        return service.addMessageBonus(message);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("path")
    public ResponseDto findPath(@RequestBody MessageDto message) {
        return service.findPath(message);
    }
}
