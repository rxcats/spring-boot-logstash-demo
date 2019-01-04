package io.github.rxcats.springbootlogstashdemo.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BuyItemLog {

    private Integer itemId;

    private Long itemQty;

    private String options;

    private LocalDateTime eventDate;

    private UserInfo userInfo;

}
