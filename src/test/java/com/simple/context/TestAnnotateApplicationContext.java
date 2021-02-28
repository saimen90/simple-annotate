package com.simple.context;

import org.junit.Test;

public class TestAnnotateApplicationContext {

    @Test
    public void AnnotateConfigApplicationContext()
    {
        String pack = "com.simple.entity";
        AnnotateApplicationContext context = new AnnotateApplicationContext(pack);


        Object t = context.getBean("t");
        System.out.println(t);


        Object user = context.getBean("user");
        System.out.println(user);

    }
}
