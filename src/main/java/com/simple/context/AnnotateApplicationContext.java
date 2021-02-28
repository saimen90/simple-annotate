package com.simple.context;

import com.google.common.base.CaseFormat;
import com.simple.annotate.Autowired;
import com.simple.annotate.Component;
import com.simple.annotate.Qualifier;
import com.simple.annotate.Value;
import com.simple.tools.ClassUtil;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotateApplicationContext {

    private Map<String, Object> ioc = new HashMap<>();

    public AnnotateApplicationContext(String pack) {
        Set<BeanDefinition> beanDefinitions = findBeanDefinition(pack);
        // 创建bean
        createObject(beanDefinitions);
        // 自动装载
        autoWireObject(beanDefinitions);
    }


    public void autoWireObject(Set<BeanDefinition> beanDefinitions) {

        Iterator<BeanDefinition> iterator = beanDefinitions.iterator();
        while (iterator.hasNext()) {
            BeanDefinition beanDefinition = iterator.next();
            Class clazz = beanDefinition.getBeanClass();
            try {
                // 完成属性的赋值
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    Autowired autoWiredAnnotation = declaredField.getAnnotation(Autowired.class);
                    // 有存在@Autowired注解
                    if (autoWiredAnnotation != null) {
                        Qualifier qualifierAnnotation = declaredField.getAnnotation(Qualifier.class);
                        if (qualifierAnnotation != null) {
                            // byName
                            Object bean = getBean(qualifierAnnotation.value());
                            String fieldName = declaredField.getName();
                            // 赋值
                            // setFunction
                            String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                            Method method = clazz.getMethod(methodName, declaredField.getType());
                            //  ConvertUtils.convert 数据类型转换
                            if ("java.util.Date".equals(declaredField.getType().getName())) {
                                // 处理时间格式
                                DateConverter dateConverter = new DateConverter();
                                // 设置日期格式
                                dateConverter.setPatterns(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
                                // 注册格式
                                ConvertUtils.register(dateConverter, java.util.Date.class);
                            }
                            Object object = getBean(beanDefinition.getBeanName());
                            method.invoke(object, ConvertUtils.convert(bean, declaredField.getType()));
                        } else {
                            // byType
                            System.out.println(">>>>"+clazz+"===="+"declaredField>"+declaredField+"\n"+"属性"+declaredField.getType().getName());

                            String packageName = declaredField.getType().getPackage().getName();
                            String beanName = CaseFormat.UPPER_CAMEL.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, declaredField.getType().getName());
                            beanName = beanName.replaceAll(packageName + ".", "");
                            Object bean = getBean(beanName);

                            System.out.println(beanName+"获取的对象》》》"+bean);
                            String fieldName = declaredField.getName();
                            String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                            Method method = clazz.getMethod(methodName, declaredField.getType());

                            //  ConvertUtils.convert 数据类型转换
                            if ("java.util.Date".equals(declaredField.getType().getName())) {
                                // 处理时间格式
                                DateConverter dateConverter = new DateConverter();
                                // 设置日期格式
                                dateConverter.setPatterns(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
                                // 注册格式
                                ConvertUtils.register(dateConverter, java.util.Date.class);
                            }
                            Object object = getBean(beanDefinition.getBeanName());
                            method.invoke(object, ConvertUtils.convert(bean, declaredField.getType()));
                        }
                    }
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
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
                // 完成属性的赋值
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    Value valAnnotation = declaredField.getAnnotation(Value.class);
                    // 有存在@Value注解
                    if (valAnnotation != null) {
                        String value = valAnnotation.value();
                        String fieldName = declaredField.getName();
                        // setFunction
                        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Method method = clazz.getMethod(methodName, declaredField.getType());
                        //  ConvertUtils.convert 数据类型转换
                        if ("java.util.Date".equals(declaredField.getType().getName())) {
                            // 处理时间格式
                            DateConverter dateConverter = new DateConverter();
                            // 设置日期格式
                            dateConverter.setPatterns(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
                            // 注册格式
                            ConvertUtils.register(dateConverter, java.util.Date.class);
                        }
                        method.invoke(object, ConvertUtils.convert(value, declaredField.getType()));
                    }
                    System.out.println(declaredField);
                }


                // 存入容器
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
