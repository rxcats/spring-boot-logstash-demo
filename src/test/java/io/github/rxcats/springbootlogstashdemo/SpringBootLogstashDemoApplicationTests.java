package io.github.rxcats.springbootlogstashdemo;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootLogstashDemoApplicationTests {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void localDateTimeJsonTest() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(new DateObject()));
    }

    @Data
    static class DateObject {
        LocalDateTime dateTime = LocalDateTime.now();
    }

}

