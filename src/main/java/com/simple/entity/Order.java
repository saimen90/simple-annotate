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
    @Value("15.88")
    float price;
    @Value("2022-01-01 12:10:06")
    Date createTime;
}
