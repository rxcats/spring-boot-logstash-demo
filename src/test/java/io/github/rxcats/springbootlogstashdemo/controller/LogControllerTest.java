package io.github.rxcats.springbootlogstashdemo.controller;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LogControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void sendLogTest() {
        String response = restTemplate.postForObject("http://localhost:" + port + "/log/buyitem", null, String.class);
        log.info("response:{}", response);
    }

    @Test
    void sendLogBulkTest() {
        IntStream.rangeClosed(1, 100000).parallel().forEach(i -> {
            restTemplate.postForObject("http://localhost:" + port + "/log/buyitem", null, String.class);
            if (i % 1000 == 1) {
                log.info("i:{}", i);
            }
        });
    }

}