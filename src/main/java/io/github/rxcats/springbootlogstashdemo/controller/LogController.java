package io.github.rxcats.springbootlogstashdemo.controller;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.rxcats.springbootlogstashdemo.entity.BuyItemLog;
import io.github.rxcats.springbootlogstashdemo.entity.UserInfo;

@RestController
public class LogController {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Value("${logstash.prefix.key}")
    String logKey;

    private UserInfo dummyUser() {
        int i = new Random().nextInt(99999999) + 1;
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("" + i);
        userInfo.setNickname("더미유저#" + i);
        userInfo.setPlatformType("guest");
        userInfo.setPlatformId("player#" + i);
        return userInfo;
    }

    @PostMapping(value = "/log/buyitem")
    public ResponseEntity sendLog() {

        BuyItemLog logBody = new BuyItemLog();
        logBody.setEventDate(LocalDateTime.now());
        logBody.setItemId(1);
        logBody.setItemQty(1L);
        logBody.setPriceType("gold");
        logBody.setPrice(100L);
        logBody.setUserInfo(dummyUser());

        redisTemplate.opsForList().leftPush(logKey, logBody);

        return ResponseEntity.ok(logBody);
    }

}
