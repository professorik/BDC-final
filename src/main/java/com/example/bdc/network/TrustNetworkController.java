package com.example.bdc.network;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TrustNetworkController {
    @Autowired
    TrustNetworkService service;

    @PostMapping("messages")
    public String addMessage() {
        // TODO: implement
        return "Hello, World!";
    }

    @PostMapping("path")
    public String findPath() {
        // TODO: implement
        return "Hello, World!";
    }
}
