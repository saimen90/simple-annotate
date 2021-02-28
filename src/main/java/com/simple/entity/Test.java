package com.simple.entity;

import com.simple.annotate.Autowired;
import com.simple.annotate.Component;
import com.simple.annotate.Qualifier;
import com.simple.annotate.Value;
import lombok.Data;

import java.util.Date;


@Data
@Component("t")  // 自定义注解
public class Test {

    @Autowired
    private Order order;
}
