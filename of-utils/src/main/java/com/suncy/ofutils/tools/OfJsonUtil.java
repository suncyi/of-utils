package com.suncy.ofutils.tools;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

/**
 * @auther suncy
 * @Date 2020/11/14  11:39
 */
public class OfJsonUtil {
    private static final ObjectMapper OBJECT_MAPPER;
    private static final ObjectMapper PRETTY_OBJECT_MAPPER;
    private static final TypeFactory TYPE_FACTORY = TypeFactory.defaultInstance();

    static {
        OBJECT_MAPPER = newBasicObjectMapper();
        // 唯一得差别就是序列化时，使用了indent
        PRETTY_OBJECT_MAPPER = newBasicObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    private static ObjectMapper newBasicObjectMapper() {
        ObjectMapper basicOm = new ObjectMapper();
        basicOm.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // JsonFactory.Feature.INTERN_FIELD_NAMES
        // 默认为true，内部会使用String.intern()，部分讨论参见 http://hellojava.info/?p=514
        // 这个可能导致性能问题，因此，默认更改为false，官方
        // https://github.com/FasterXML/jackson-core/issues/378
        // 表示，3.0版本将默认为false
        basicOm.getFactory().disable(JsonFactory.Feature.INTERN_FIELD_NAMES);
        return basicOm;
    }

    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T fromJson(String jsonStr, Type type) {
        return JSON.parseObject(jsonStr, type);
    }

    public static <T> T parseJson(String jsonStr, Class<T> clazz) {
        //        requireNonBlankJson(jsonStr);
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.<T>readValue(jsonStr, clazz);
        } catch (Exception e) {
            throw new RuntimeException("从json " + jsonStr + " 转化为" + clazz + "对象失败", e);
        }
    }

    public static <T> T parseJson(String jsonStr, TypeReference<T> valueTypeRef) {
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.<T>readValue(jsonStr, valueTypeRef);
        } catch (Exception e) {
            throw new RuntimeException("从json " + jsonStr + " 转化为" + valueTypeRef + "对象失败", e);
        }
    }

    public static <T> T parseJson(String jsonStr, Type type) {
        Class javaType = TypeFactory.rawClass(type);
        try {
            return (T) OBJECT_MAPPER.<T>readValue(jsonStr, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("从json " + jsonStr + " 转化为" + type + "对象失败", e);
        }
    }

}
