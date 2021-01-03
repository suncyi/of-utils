package com.suncy.ofutils.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author suncy
 * @Date 2020/11/14  23:22
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OfTestAutowired {
    /**
     * 需要测试目标程序没有interface的情况，指定目标类
     * defautl 用当前注解，不会冲突
     *
     * @return
     */
    Class targetClzz() default OfTestAutowired.class;
}
