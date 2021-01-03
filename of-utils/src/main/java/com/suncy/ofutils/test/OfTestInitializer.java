package com.suncy.ofutils.test;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * @auther suncy
 * @Date 2020/11/14  23:18
 */
public class OfTestInitializer {
    // 初始化测试类的属性

    /**
     * @param targetTest 单元测试对象
     * @param serverPort
     * @throws IllegalAccessException
     */
    public static void initTest(Object targetTest, int serverPort) throws IllegalAccessException {
        // OfTestAutowire field = targetTest.getClass().getAnnotation(OfTestAutowire.class);
        Field[] fields = targetTest.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(OfTestAutowired.class)) {
                field.setAccessible(true);
                // junit 属性类型
                Class fieldType = field.getType();
                OfTestAutowired ofTestAutowired = field.getAnnotation(OfTestAutowired.class);
                // 根据注解的属性
                Class invokeTargetClz = ofTestAutowired.targetClzz();
                OfTestProxyHandler handler = null;
                if (Objects.equals(OfTestAutowired.class, invokeTargetClz)) {
                    // 默认情况
                    handler = new OfTestProxyHandler(fieldType, serverPort);
                } else {
                    handler = new OfTestProxyHandler(invokeTargetClz, serverPort);
                }
                Object proxy = Proxy
                        .newProxyInstance(invokeTargetClz.getClassLoader(), new Class[] { fieldType }, handler);
                field.set(targetTest, proxy);
            }
        }
    }
}
