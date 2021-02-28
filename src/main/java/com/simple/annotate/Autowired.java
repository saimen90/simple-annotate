package com.simple.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // 给属性 加
@Retention(RetentionPolicy.RUNTIME) //运行时
public @interface Autowired {
}
