package com.simple.entity;

import com.simple.annotate.Component;
import com.simple.annotate.Value;
import lombok.Data;

import java.util.Date;


@Data
@Component  // 自定义注解
public class Order {
    @Value("1")
    Integer orderId;
    @Value("2021022801000001")
    String orderSn;
    @Value("商品名称")
    String title;
    @Value("2021-02-28 10:10:01")
    Date createTime;
}
