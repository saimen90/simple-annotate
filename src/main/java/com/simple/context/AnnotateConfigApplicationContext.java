package com.simple.context;

import com.simple.tools.ClassUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AnnotateConfigApplicationContext {


    public AnnotateConfigApplicationContext(String pack) {

        Set<BeanDefinition> beanDefinition = findBeanDefinition(pack);


    }

    // 获取 包下所有类
    // 遍历类，找到添加注解的类
    //  奖这些类封装成 BeanDefinition ，装载到集合中
    public Set<BeanDefinition> findBeanDefinition(String pack) {

        ClassUtil classUtil = new ClassUtil();
        List<Class<?>> classList = classUtil.getClassList(pack,true, null);

        Iterator<Class<?>> iterator = classList.iterator();
        while (iterator.hasNext()) {

            System.out.println(iterator.next());

        }

        return null;
    }
}
