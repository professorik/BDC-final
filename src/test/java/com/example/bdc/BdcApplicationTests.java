package com.example.bdc;

import com.example.bdc.network.message.TrustNetworkController;
import com.example.bdc.network.user.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BdcApplicationTests {

    @Autowired
    private UserController userController;

    @Autowired
    private TrustNetworkController networkController;

    @Test
    void contextLoads() {
        assertThat(userController).isNotNull();
        assertThat(networkController).isNotNull();
    }

}
