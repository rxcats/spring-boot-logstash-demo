package io.github.rxcats.springbootlogstashdemo.controller.es;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void searchParallelTest() {
        IntStream.rangeClosed(1, 10000).parallel().forEach(i -> {
            List<Integer> froms = Arrays.asList(0, 20, 40, 60, 80, 100, 120, 140, 160, 180, 200, 220, 240, 260, 280, 300, 320, 340, 360, 380, 400);
            Integer from = froms.get(new Random().nextInt(froms.size()));

            StopWatch sw = new StopWatch();
            sw.start();
            String response = restTemplate.getForObject("http://localhost:" + port + "/search/" + from, String.class);
            sw.stop();
            log.info("ms:{}, from:{}, response:{}", sw.getTotalTimeMillis(), from, response);
        });
    }

}
