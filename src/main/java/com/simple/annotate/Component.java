package com.simple.annotate;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // 给类 加
@Retention(RetentionPolicy.RUNTIME) //运行时
public @interface Component {
    String value() default "";
}
