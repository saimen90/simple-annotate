package com.simple.entity;

import lombok.Data;

import java.util.Date;


@Data
public class User {

    Integer id;
    String name;
    String password;
    Date loginTime;
    String loginIp;

}
