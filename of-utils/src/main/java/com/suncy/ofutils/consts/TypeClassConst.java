package com.suncy.ofutils.consts;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @auther suncy
 * @Date 2020/11/15  13:57
 */
public class TypeClassConst {
    public static Map<String, Class> BASE_NAME_TYPE_MAP = ImmutableMap.<String, Class>builder()
            .put(int.class.getName(), int.class).put(byte.class.getName(), byte.class)
            .put(char.class.getName(), char.class).put(short.class.getName(), short.class)
            .put(long.class.getName(), long.class).put(float.class.getName(), float.class)
            .put(double.class.getName(), double.class).put(boolean.class.getName(), boolean.class).build();
}
