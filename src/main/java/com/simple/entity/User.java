package com.simple.entity;

import com.simple.annotate.Component;
import com.simple.annotate.Value;
import lombok.Data;

import java.util.Date;


@Data
@Component  // 自定义注解
public class User {
    @Value("1")
    Integer id;
    @Value("李四")
    String name;
    @Value("123456")
    String password;
    @Value("2021-02-28 10:10:01")
    Date loginTime;
    @Value("127.0.0.1")
    String loginIp;
}
