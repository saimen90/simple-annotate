package com.simple.context;

import org.junit.Test;

public class TestAnnotateConfigApplicationContext {

    @Test
    public void AnnotateConfigApplicationContext()
    {
        String pack = "com.simple.entity";
        AnnotateConfigApplicationContext context = new AnnotateConfigApplicationContext(pack);
    }
}
