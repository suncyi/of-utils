package com.suncy.ofutils.tools;

import com.suncy.ofutils.consts.TypeClassConst;

/**
 * @auther suncy
 * @Date 2020/11/15  14:03
 */
public class ReflectUtil {

    public static Class name2Class(String clzName) {
        Class clz = TypeClassConst.BASE_NAME_TYPE_MAP.get(clzName);
        if (null != clz) {
            return clz;
        }
        try {
            return Class.forName(clzName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("name 2 class 报错", e);
        }
    }

}
