package com.simple.context;

import com.google.common.base.CaseFormat;
import com.simple.annotate.Component;
import com.simple.tools.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class AnnotateApplicationContext {

    private Map<String, Object> ioc = new HashMap<>();

    public AnnotateApplicationContext(String pack) {
        Set<BeanDefinition> beanDefinitions = findBeanDefinition(pack);
        createObject(beanDefinitions);
    }

    public Object getBean(String beanName) {
        return ioc.get(beanName);
    }

    // 根据原材料动态 创建bean
    public void createObject(Set<BeanDefinition> beanDefinitions) {
        Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
        while (iterator.hasNext()) {
            BeanDefinition beanDefinition = iterator.next();

            String beanName = beanDefinition.getBeanName();
            Class clazz = beanDefinition.getBeanClass();

            // 无参数构建
            try {
                Object object = clazz.getConstructor().newInstance();
                ioc.put(beanName, object);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }


    // 获取 包下所有类
    // 遍历类，找到添加注解的类
    //  奖这些类封装成 BeanDefinition ，装载到集合中
    public Set<BeanDefinition> findBeanDefinition(String pack) {

        Set<BeanDefinition> beanDefinitions = new HashSet<>();

        ClassUtil classUtil = new ClassUtil();
        List<Class<?>> classList = classUtil.getClassList(pack, true, null);

        Iterator<Class<?>> iterator = classList.iterator();
        while (iterator.hasNext()) {
            // 遍历类，找到添加注解的类
            Class<?> clazz = iterator.next();
            Component annotationComponent = clazz.getAnnotation(Component.class);
            // 添加了 Component 注解
            if (annotationComponent != null) {
                String beanName = annotationComponent.value();
                if ("".equals(beanName)) {
                    // 类的包名
                    String packageName = clazz.getPackage().getName();
                    String claName = clazz.getName().replace(packageName + ".", "");
                    // 获取类目-小驼峰
                    beanName = CaseFormat.UPPER_CAMEL.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, claName);
                }
                BeanDefinition beanDefinition = new BeanDefinition(beanName, clazz);
                beanDefinitions.add(beanDefinition);
            }
        }
        return beanDefinitions;
    }
}
