package io.github.rxcats.springbootlogstashdemo.repository.es;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

import io.github.rxcats.springbootlogstashdemo.entity.BuyItemLog;

@Slf4j
@SpringBootTest
class BuyItemLogRepositoryTest {

    @Autowired
    BuyItemLogRepository repository;

    @Test
    void searchTest() {
        List<BuyItemLog> result = repository.search(0, 10);
        log.info("result:{}", result);
    }

}