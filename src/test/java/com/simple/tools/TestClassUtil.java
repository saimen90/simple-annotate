package com.simple.tools;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class TestClassUtil {
    @Test
    public void TestClassUtil()
    {

        ClassUtil classUtil = new ClassUtil();
        List<Class<?>> classList = classUtil.getClassList("com.simple",true, null);
        System.out.println("类"+classList);
    }
}

